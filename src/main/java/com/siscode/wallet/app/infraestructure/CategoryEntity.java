package com.siscode.wallet.app.infraestructure;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("category")
public class CategoryEntity {

    @Id
    private String _id;
    private String name;
    private String icon;
    private String createdAt;
    private String updatedAt;

}
