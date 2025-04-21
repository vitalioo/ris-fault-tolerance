package com.example.workerservice.service;

import com.example.workerservice.model.exception.BruteForceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BruteForceService {
    private String md5Hex(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new BruteForceException(e.getLocalizedMessage());
        }
    }

    public List<String> processTask(String hash, int maxLength, List<String> alphabet, int partNumber, int partCount) {
        int n = alphabet.size();
        long total = 0;
        for (int l = 1; l <= maxLength; l++) {
            total += Math.pow(n, l);
        }
        long startIndex = total * partNumber / partCount;
        long endIndex = total * (partNumber + 1) / partCount;
        String target = hash.toLowerCase();
        List<String> found = new ArrayList<>();
        for (long idx = startIndex; idx < endIndex; idx++) {
            String word = indexToWord(idx, maxLength, alphabet);
            if (word == null) continue;
            String calc = md5Hex(word);
            if (calc.equalsIgnoreCase(target)) {
                found.add(word);
            }
        }
        return found;
    }

    private String indexToWord(long index, int maxLength, List<String> alphabet) {
        int n = alphabet.size();
        long sum = 0;
        for (int length = 1; length <= maxLength; length++) {
            long count = (long) Math.pow(n, length);
            if (index < sum + count) {
                long rank = index - sum;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    long power = (long) Math.pow(n, length - i - 1);
                    long pos = rank / power;
                    sb.append(alphabet.get((int) pos));
                    rank = rank % power;
                }
                return sb.toString();
            }
            sum += count;
        }
        return null;
    }
}