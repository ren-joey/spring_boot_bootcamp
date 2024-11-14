package example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OrderResponseDto {

    @Schema(description = "ID of the order", example = "1")
    private Long id;

    @Schema(description = "Name of the order", example = "My Order")
    private String name;

    @Schema(description = "Price of the order", example = "100.00")
    private BigDecimal price;

    @Schema(description = "Username of the user who created the order", example = "user123")
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
