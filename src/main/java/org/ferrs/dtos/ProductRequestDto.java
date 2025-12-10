package org.ferrs.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductRequestDto {
  private String name;
  private Double price;
  private Double tax;

}

