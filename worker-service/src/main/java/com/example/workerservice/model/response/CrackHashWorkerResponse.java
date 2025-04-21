package com.example.workerservice.model.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CrackHashWorkerResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CrackHashWorkerResponse {

    @XmlElement(name = "RequestId")
    private String requestId;

    @XmlElement(name = "FoundWords")
    private String foundWords;

    public CrackHashWorkerResponse() {
    }

    public CrackHashWorkerResponse(String requestId, String foundWords) {
        this.requestId = requestId;
        this.foundWords = foundWords;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getFoundWords() {
        return foundWords;
    }

    public void setFoundWords(String foundWords) {
        this.foundWords = foundWords;
    }
}
