package com.example.workerservice.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlRootElement(name = "CrackHashManagerRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"requestId", "partNumber", "partCount", "hash", "maxLength", "alphabet"})
public class CrackHashManagerRequest {

    @XmlElement(name = "RequestId", required = true)
    private String requestId;

    @XmlElement(name = "PartNumber")
    private int partNumber;

    @XmlElement(name = "PartCount")
    private int partCount;

    @XmlElement(name = "Hash", required = true)
    private String hash;

    @XmlElement(name = "MaxLength")
    private int maxLength;

    @XmlElement(name = "Alphabet", required = true)
    private List<String> alphabet;

    public CrackHashManagerRequest() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }
}
