package com.siscode.wallet.app.infraestructure;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("subcategory")
public class SubCategoryEntity {

    @Id
    private String _id;
    private String name;
    private String icon;
    @DBRef
    private CategoryEntity category;

}
