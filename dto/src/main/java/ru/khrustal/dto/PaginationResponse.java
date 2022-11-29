package ru.khrustal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {
    private Integer page;
    private Integer pageSize;
    private Integer totalElements;
    private List<T> items;

    public void addItem(T item) {
        this.items.add(item);
    }
}
