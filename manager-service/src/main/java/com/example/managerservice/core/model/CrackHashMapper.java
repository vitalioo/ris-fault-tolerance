package com.example.managerservice.core.model;

import com.example.managerservice.core.entity.Task;
import com.example.managerservice.core.model.xml.CrackHashManagerRequest;

import java.util.List;

public final class CrackHashMapper {
    private static final List<String> alphabet = List.of(
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    );

    public static CrackHashManagerRequest mapToXmlRequest(Task task, int partCount, int workerCount) {
        CrackHashManagerRequest request = new CrackHashManagerRequest();
        request.setRequestId(task.getRequestId());
        request.setPartNumber(partCount);
        request.setPartCount(workerCount);
        request.setHash(task.getHash());
        request.setMaxLength(task.getMaxLength());
        request.setAlphabet(alphabet);

        return request;
    }
}
