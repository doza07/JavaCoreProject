package com.doza.dto;

public record PersonFilter(String firstName,
                           String lastName,
                           int limit,
                           int offset) {

}
