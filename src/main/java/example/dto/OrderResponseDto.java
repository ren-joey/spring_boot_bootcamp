package example.dto;

import java.math.BigDecimal;

public class OrderResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String username;

    public OrderResponseDto() {
    }

    public OrderResponseDto(
            Long id,
            String name,
            BigDecimal price,
            String username
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.username = username;
    }

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
