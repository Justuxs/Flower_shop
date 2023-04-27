package PSK.FlowerShop.controller;

import PSK.FlowerShop.request.AddProductDTO;
import PSK.FlowerShop.request.ProductDTO;
import PSK.FlowerShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody AddProductDTO request) {
        try {
            ProductDTO product = productService.createProduct(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(product);
        } catch (Exception e) {
            ProductDTO errorProductDTO = new ProductDTO();
            errorProductDTO.setErrorMessage(e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorProductDTO);
        }
    }

//    @GetMapping("")
//    public ResponseEntity<List<ProductDTO>> getProducts() {
//        try {
//            List<ProductDTO> productDTOs =  productService.getAllProducts();
//            return ResponseEntity.ok(productDTOs);
//        } catch (Exception e) {
//            return ResponseEntity
//                    .notFound()
//                    .build();
//        }
//    }
//
//    // TODO: this endpoint should be inside categories
//    //  as the full path should be '.../api/categories/{id}/products
//    //  After moving @GetMapping needs fixing
//    //  (should change to "{id}/products")
    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestParam(name = "categoryId", required = false) UUID id) {
        try {
            List<ProductDTO> productDTOs;

            if(id == null)
                productDTOs = productService.getAllProducts();
            else
                productDTOs = productService.getProductsByCategory(id);

            return ResponseEntity.ok(productDTOs);
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        try {
            ProductDTO productDTO =  productService.getProductById(id);
            return ResponseEntity.ok(productDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> updateProductById(@RequestBody AddProductDTO request,
                                                        @PathVariable UUID id) {
        try {
            ProductDTO productDTO =  productService.updateProduct(id, request);
            return ResponseEntity.ok(productDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity
                    .noContent()
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}