package com.example.demo.dto;

import java.util.List;

public class PersonProfessionListDto {
    public static Integer personsOnPage = 10;
    private List<PersonProfessionDto> data;
    private Integer pagesCount;
    private Integer currentPage;
    private Integer onPage;

    public PersonProfessionListDto(List<PersonProfessionDto> data, Integer pagesCount, Integer currentPage) {
        this.data = data;
        this.pagesCount = pagesCount;
        this.currentPage = currentPage;
        this.onPage = PersonProfessionListDto.personsOnPage;
    }

    public void setOnPage(Integer onPage) {
        this.onPage = onPage;
    }

    public Integer getOnPage() {
        return onPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public List<PersonProfessionDto> getData() {
        return data;
    }

    public Integer getPagesCount() {
        return pagesCount;
    }

    public void setData(List<PersonProfessionDto> data) {
        this.data = data;
    }

    public void setPagesCount(Integer pagesCount) {
        this.pagesCount = pagesCount;
    }
}
