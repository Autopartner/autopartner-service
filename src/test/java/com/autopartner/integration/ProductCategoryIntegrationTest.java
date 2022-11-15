package com.autopartner.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.autopartner.api.dto.request.ProductCategoryRequest;
import com.autopartner.api.dto.request.ProductCategoryRequestFixture;
import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.api.dto.request.TaskCategoryRequestFixture;
import com.autopartner.domain.ProductCategory;
import com.autopartner.domain.ProductCategoryFixture;
import com.autopartner.repository.ProductCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCategoryIntegrationTest extends AbstractIntegrationTest{

  public static final String PRODUCT_CATEGORY_URL = "/api/v1/product-categories";
  @Autowired
  ProductCategoryRepository productCategoryRepository;

  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp();
    productCategoryRepository.deleteAll();
  }

  @Test
  void givenProductCategoryRequest_whenCreateProductCategory_thenReturnSavedProductCategory() throws Exception {
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequestWithoutParent();

    ResultActions result = mockMvc.perform(post(PRODUCT_CATEGORY_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name", is(request.getName())));
  }

  @Test
  void givenDuplicatedProductCategoryName_whenCreateProductCategory_thenReturn400() throws Exception {

    productCategoryRepository.save(ProductCategoryFixture.createProductCategoryWithoutParent());
    ProductCategoryRequest category = ProductCategoryRequestFixture.createProductCategoryRequestWithoutParent();

    ResultActions result = mockMvc.perform(post(PRODUCT_CATEGORY_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(category)));

    result
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", is("ProductCategory with param: ProductCategory already exists")));
  }

  @Test
  public void givenListOfProductCategories_whenGetAllProductCategories_thenReturnProductCategories() throws Exception {

    List<ProductCategory> productCategories = new ArrayList<>();
    productCategories.add(ProductCategoryFixture.createProductCategory());
    productCategories.add(ProductCategoryFixture.createProductCategoryWithDifferentName());
    productCategoryRepository.saveAll(productCategories);

    ResultActions response = mockMvc.perform(get(PRODUCT_CATEGORY_URL)
        .header(HttpHeaders.AUTHORIZATION, token));

    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()",
            is(productCategories.size())));
  }

  @Test
  public void givenClientId_whenGetProductCategoryById_thenReturnProductCategoryResponse() throws Exception {

    ProductCategory parentCategory = productCategoryRepository.save(ProductCategoryFixture.createParentCategory());
    ProductCategory productCategory = productCategoryRepository.save(ProductCategoryFixture.createProductCategory());

    ResultActions response = mockMvc.perform(get(PRODUCT_CATEGORY_URL + "/{id}", productCategory.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(productCategory.getName())))
        .andExpect(jsonPath("$.parentId", is(productCategory.getParentId()), Long.class));

  }

  @Test
  public void givenInvalidProductCategoryId_whenGetProductCategoryById_thenReturn404() throws Exception {

    ProductCategory category = productCategoryRepository.save(ProductCategoryFixture.createProductCategory());
    long id = category.getId() + 100;

    ResultActions response = mockMvc.perform(get(PRODUCT_CATEGORY_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("ProductCategory with id=" + id + " is not found")));
  }

  @Test
  public void givenUpdatedProductCategoryRequest_whenUpdateProductCategory_thenReturnUpdatedProductCategoryResponse() throws Exception{

    ProductCategory parentCategory = productCategoryRepository.save(ProductCategoryFixture.createParentCategory());
    productCategoryRepository.save(parentCategory);

    ProductCategory category = productCategoryRepository.save(ProductCategoryFixture.createProductCategoryWithoutParent());
    productCategoryRepository.save(category);

    ProductCategoryRequest request = ProductCategoryRequest.builder()
        .name("New")
        .parentId(parentCategory.getId())
        .build();

    ResultActions response = mockMvc.perform(put(PRODUCT_CATEGORY_URL + "/{id}", category.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(request.getName())))
        .andExpect(jsonPath("$.parentId", is(request.getParentId()), Long.class));
  }

  @Test
  public void givenInvalidProductCategoryId_whenUpdateProductCategory_thenReturn404() throws Exception{

    ProductCategory category = productCategoryRepository.save(ProductCategoryFixture.createProductCategory());
    long id = category.getId() + 100;
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();

    ResultActions response = mockMvc.perform(put(PRODUCT_CATEGORY_URL + "/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("ProductCategory with id=" + id +" is not found")));
  }

  @Test
  public void givenDuplicatedProductCategoryName_whenUpdateProductCategory_thenReturn400() throws Exception{

    ProductCategory category1 = productCategoryRepository.save(ProductCategoryFixture.createProductCategoryWithoutParent());
    ProductCategory category2 = productCategoryRepository.save(ProductCategoryFixture.createProductCategoryWithDifferentName());
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequestWithoutParent().withName(category2.getName());

    ResultActions response = mockMvc.perform(put(PRODUCT_CATEGORY_URL + "/{id}", category1.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("ProductCategory with param: ProductCategory2 already exists")));
  }

  @Test
  public void givenEqualsId_whenUpdateProductCategory_thenReturn400() throws Exception{

    ProductCategory category = productCategoryRepository.save(ProductCategoryFixture.createProductCategory());
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequest().withParentId(category.getId());

    ResultActions response = mockMvc.perform(put(PRODUCT_CATEGORY_URL + "/{id}", category.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("ParentId: " + category.getId() + " equals current id: " + category.getId())));
  }

  @Test
  void givenProductCategoryId_whenDeleteProductCategory_thenReturn200() throws Exception {

    ProductCategory category = productCategoryRepository.save(ProductCategoryFixture.createProductCategory());

    ResultActions result = mockMvc.perform(delete(PRODUCT_CATEGORY_URL + "/{id}", category.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void givenInvalidProductCategoryId_whenDeleteProductCategory_thenReturn404() throws Exception{

    ProductCategory category = productCategoryRepository.save(ProductCategoryFixture.createProductCategory());
    long id = category.getId() + 100;

    ResultActions response = mockMvc.perform(delete(PRODUCT_CATEGORY_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("ProductCategory with id=" + id +" is not found")));
  }

}
