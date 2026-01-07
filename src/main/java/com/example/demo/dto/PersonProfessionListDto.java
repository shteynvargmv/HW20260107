package com.example.demo.dto;

import com.example.demo.entity.Person;
import com.example.demo.entity.Profession;

import java.util.List;

public class PersonProfessionListDto {
    public static final Integer PERSONS_ON_PAGE = 10;
    private List<PersonProfessionDto> data;
    private Integer pagesCount;
    private Integer currentPage;

    public PersonProfessionListDto(List<PersonProfessionDto> data) {
        this.data = data;
        this.pagesCount = data.size() / PERSONS_ON_PAGE;
        if ( (data.size() % PERSONS_ON_PAGE) != 0 ) {
            this.pagesCount += 1;
        }
        this.currentPage = 1;
    }

    public PersonProfessionListDto(List<PersonProfessionDto> data, Integer page) {
        this.data = data;
        this.pagesCount = data.size() / PERSONS_ON_PAGE;
        if ( (data.size() % PERSONS_ON_PAGE) != 0 ) {
            this.pagesCount += 1;
        }
        this.currentPage = page;
    }

    public PersonProfessionListDto(List<PersonProfessionDto> data, Integer pagesCount, Integer currentPage) {
        this.data = data;
        this.pagesCount = pagesCount;
        this.currentPage = currentPage;
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
