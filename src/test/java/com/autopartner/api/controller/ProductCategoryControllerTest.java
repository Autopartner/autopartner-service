package com.autopartner.api.controller;

import static com.autopartner.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.autopartner.api.dto.request.ProductCategoryRequest;
import com.autopartner.api.dto.request.ProductCategoryRequestFixture;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.api.dto.response.ProductCategoryResponse;
import com.autopartner.domain.ProductCategory;
import com.autopartner.domain.ProductCategoryFixture;
import com.autopartner.service.ProductCategoryService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = ProductCategoryController.class)
@AutoConfigureMockMvc
public class ProductCategoryControllerTest extends AbstractControllerTest{

  public static final String URL = "/api/v1/product-categories";

  @MockBean
  ProductCategoryService productCategoryService;

  @Test
  void getAll_NotAuthorized_ReturnsError() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsProductCategories() throws Exception {
    ProductCategory category = ProductCategoryFixture.createProductCategory();
    ProductCategoryResponse response = ProductCategoryResponse.fromEntity(category);
    List<ProductCategoryResponse> responses = List.of(response);
    when(productCategoryService.findAll(any())).thenReturn(List.of(category));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(responses)));
  }

  @Test
  void get_ValidProductCategoryId_ReturnsProductCategory() throws Exception {
    ProductCategory category = ProductCategoryFixture.createProductCategory();
    ProductCategoryResponse response = ProductCategoryResponse.fromEntity(category);
    long id = category.getId();
    when(productCategoryService.findById(eq(id), any())).thenReturn(Optional.of(category));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void get_InvalidProductCategoryId_ReturnsError() throws Exception {
    long id = 1L;
    when(productCategoryService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "ProductCategory with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ProductCategoryAlreadyExists_ReturnsError() throws Exception {
    ProductCategory category = ProductCategoryFixture.createProductCategory();
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequest();
    long id = 1L;
    when(productCategoryService.findById(eq(id), any())).thenReturn(Optional.of(category));
    when(productCategoryService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.of(1L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "ProductCategory with param: ProductCategory already exists");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesProductCategory() throws Exception {
    ProductCategory category = ProductCategoryFixture.createProductCategory();
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequest();
    ProductCategoryResponse response = ProductCategoryResponse.fromEntity(category);
    when(productCategoryService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.empty());
    when(productCategoryService.create(eq(request), any())).thenReturn(category);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_ValidRequest_UpdatesProductCategory() throws Exception {
    ProductCategory category = ProductCategoryFixture.createProductCategory();
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequest();
    ProductCategoryResponse response = ProductCategoryResponse.fromEntity(category);
    long id = category.getId();
    when(productCategoryService.findById(eq(id), any())).thenReturn(Optional.of(category));
    when(productCategoryService.update(category, request)).thenReturn(category);
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidProductCategoryId_ReturnsError() throws Exception {
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequest();
    long id = 1L;
    when(productCategoryService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "ProductCategory with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ProductCategoryAlreadyExists_ReturnsError() throws Exception {
    ProductCategory category = ProductCategoryFixture.createProductCategory();
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequest();
    long id = 1L;
    when(productCategoryService.findById(eq(id), any())).thenReturn(Optional.of(category));
    when(productCategoryService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.of(100L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "ProductCategory with param: ProductCategory already exists");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_InvalidProductCategoryId_ReturnsError() throws Exception {
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequest();
    long id = 1L;
    when(productCategoryService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "ProductCategory with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesProductCategory() throws Exception {
    ProductCategory category = ProductCategoryFixture.createProductCategory();
    long id = 1L;
    when(productCategoryService.findById(eq(id), any())).thenReturn(Optional.of(category));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful());
  }

}
