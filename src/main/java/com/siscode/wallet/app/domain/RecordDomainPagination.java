package com.siscode.wallet.app.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RecordDomainPagination {

    List<RecordDomainComplete> content;
    long totalElements;
    int pageNumber;
    int pageSize;
    int totalPages;

    public RecordDomainPagination() {
    }

    public RecordDomainPagination(List<RecordDomainComplete> content, long totalElements, int pageNumber, int pageSize, int totalPages) {
        this.content = content;
        this.totalElements = totalElements;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
    }

    public List<RecordDomainComplete> getContent() {
        return content;
    }

    public void setContent(List<RecordDomainComplete> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
