package example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class OrderRequestDto {
    @Schema(description = "Name of the order", example = "My Order", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name is required", groups = PutValidation.class)
    @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
    private String name;

    @Schema(description = "Price of the order", example = "100.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Price is required", groups = PutValidation.class)
    @DecimalMax(value = "100000", message = "Price must be less or equal than 100000")
    @DecimalMin(value = "10", message = "Price must be more or equal than 10")
    private BigDecimal price;

    public OrderRequestDto() {
    }

    public OrderRequestDto(String name) {
        this.name = name;
    }

    public OrderRequestDto(BigDecimal price) {
        this.price = price;
    }

    public OrderRequestDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    // Getter and Setter methods
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
}
