package com.example.music.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePageDTO<T> {
  private List<T> content;
  private int totalPages;
  private Long totalElements;

  public ResponsePageDTO() {
    this.totalPages = 1;
  }
}
