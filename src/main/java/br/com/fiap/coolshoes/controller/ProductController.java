package br.com.fiap.coolshoes.controller;

import br.com.fiap.coolshoes.dto.ProductCreateUpdateDTO;
import br.com.fiap.coolshoes.dto.ProductDTO;
import br.com.fiap.coolshoes.dto.ProductPriceUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {

    private List<ProductDTO> productDTOS = new ArrayList<>();

    public ProductController(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setCor("Branco");
        productDTO.setModelo("Nike Jordan");
        productDTO.setNumero(40);
        productDTO.setPreco(BigDecimal.valueOf(300.0));

        productDTOS.add(productDTO);

        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId(2L);
        productDTO1.setCor("Preto");
        productDTO1.setModelo("Adidas Bounce");
        productDTO1.setNumero(41);
        productDTO1.setPreco(BigDecimal.valueOf(320.0));

        productDTOS.add(productDTO1);
    }

    @GetMapping
    public List<ProductDTO> listAll(@RequestParam(required = false) Integer numero){
        return productDTOS.stream()
                .filter(productDTO -> numero == null || numero.equals(productDTO.getNumero()))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ProductDTO getById(@PathVariable Long id){
        return productDTOS.stream()
                .filter(productDTO -> id.equals(productDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "produto nao encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductCreateUpdateDTO productCreateUpdateDTO){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(productDTOS.size() + 1L);
        productDTO.setCor(productCreateUpdateDTO.getCor());
        productDTO.setPreco(productCreateUpdateDTO.getPreco());
        productDTO.setModelo(productCreateUpdateDTO.getModelo());
        productDTO.setNumero(productCreateUpdateDTO.getNumero());
        productDTOS.add(productDTO);

        return productDTO;
    }

    @PutMapping("{id}")
    public ProductDTO update(@PathVariable Long id,
                             @RequestBody ProductCreateUpdateDTO productCreateUpdateDTO){
        ProductDTO productDTO = getById(id);

        productDTO.setCor(productCreateUpdateDTO.getCor());
        productDTO.setPreco(productCreateUpdateDTO.getPreco());
        productDTO.setModelo(productCreateUpdateDTO.getModelo());
        productDTO.setNumero(productCreateUpdateDTO.getNumero());

        return productDTO;
    }

    @PatchMapping("{id}/price")
    public ProductDTO updatePrice(@PathVariable Long id,
                             @RequestBody ProductPriceUpdateDTO productPriceUpdateDTO){
        ProductDTO productDTO = getById(id);
        productDTO.setPreco(productPriceUpdateDTO.getPreco());

        return productDTO;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        ProductDTO productDTO = getById(id);
        productDTOS.remove(productDTO);
    }

}
