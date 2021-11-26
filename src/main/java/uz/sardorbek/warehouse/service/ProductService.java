package uz.sardorbek.warehouse.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


import lombok.SneakyThrows;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import uz.sardorbek.warehouse.entity.MeassurementType;
import uz.sardorbek.warehouse.entity.MeassurementValue;
import uz.sardorbek.warehouse.entity.Product;
import uz.sardorbek.warehouse.entity.ProductValueType;
import uz.sardorbek.warehouse.payload.ProductDto;
import uz.sardorbek.warehouse.repository.MeassurementTypeRepository;
import uz.sardorbek.warehouse.repository.MeassurementValueRepository;
import uz.sardorbek.warehouse.repository.ProductRepository;
import uz.sardorbek.warehouse.repository.ProductValueTypeRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MeassurementTypeRepository meassurementTypeRepository;

    @Autowired
    MeassurementValueRepository meassurementValueRepository;
    @Autowired
    ProductValueTypeRepository productValueTypeRepository;

    @Value("${FilePath}")
    String path;


    public String searchProducts(Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String typeOfSearch = request.getParameter("TypeOfSearch");


        if (typeOfSearch.equals("name")) {
            List<Product> products = productRepository.findAll();

            List<ProductDto> productDtoList = new ArrayList<>();
            long count = 0;
            for (Product product : products) {
                if (product.getName().equalsIgnoreCase(name) || product.getName().contains(name)) {
                    count++;
                    ProductValueType pvtById = productValueTypeRepository.findByProductId(product.getId());
                    productDtoList.add(new ProductDto(
                            count,
                            product.getName(),
                            product.getPrice(),
                            product.getStorageDate(),
                            pvtById.getMeassurementType().getName(),
                            pvtById.getMeassurementValue().getValue()
                    ));
                }

            }
            model.addAttribute("listProduct",productDtoList);
            return "get";
        }
        if (typeOfSearch.equals("price")) {
            List<Product> products = productRepository.searchByPrice(name);
            List<ProductDto> productDtoList = new ArrayList<>();
            long count = 0;
            for (Product product : products) {
                count++;
                ProductValueType pvtById = productValueTypeRepository.findByProductId(product.getId());
                productDtoList.add(new ProductDto(
                        count,
                        product.getName(),
                        product.getPrice(),
                        product.getStorageDate(),
                        pvtById.getMeassurementType().getName(),
                        pvtById.getMeassurementValue().getValue()
                ));
            }
            model.addAttribute("listProduct", productDtoList.stream().
                    sorted(Comparator.comparing(ProductDto::getName)).collect(Collectors.toList()));
            return "get";
        }
        if (typeOfSearch.equals("MeassurementType")) {
            List<Product> products = productRepository.searchByMType(name);
            List<ProductDto> productDtoList = new ArrayList<>();
            long count = 0;
            for (Product product : products) {
                count++;
                ProductValueType pvtById = productValueTypeRepository.findByProductId(product.getId());
                productDtoList.add(new ProductDto(
                        count,
                        product.getName(),
                        product.getPrice(),
                        product.getStorageDate(),
                        pvtById.getMeassurementType().getName(),
                        pvtById.getMeassurementValue().getValue()
                ));
            }
            model.addAttribute("listProduct", productDtoList.stream().
                    sorted(Comparator.comparing(ProductDto::getName)).collect(Collectors.toList()));
            return "get";
        }
        if (typeOfSearch.equals("MeassurementValue")) {
            List<Product> products = productRepository.searchByMValue(name);
            List<ProductDto> productDtoList = new ArrayList<>();
            long count = 0;

            for (Product product : products) {
                count++;
                ProductValueType pvtById = productValueTypeRepository.findByProductId(product.getId());
                productDtoList.add(new ProductDto(
                        count,
                        product.getName(),
                        product.getPrice(),
                        product.getStorageDate(),
                        pvtById.getMeassurementType().getName(),
                        pvtById.getMeassurementValue().getValue()
                ));
            }
            model.addAttribute("listProduct", productDtoList.stream().
                    sorted(Comparator.comparing(ProductDto::getName)).collect(Collectors.toList()));
            return "get";
        }
        if (typeOfSearch.equals("StorageDate")) {
            List<Product> products = productRepository.findAll();
            List<ProductDto> productDtoList = new ArrayList<>();

            String[] split = name.split("-");
            String year = split[0];
            String month = split[1];
            String day = split[2];
            LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

            long count = 0;
            for (Product product : products) {
                if (product.getStorageDate().equals(localDate)) {
                    count++;
                    ProductValueType pvtById = productValueTypeRepository.findByProductId(product.getId());
                    productDtoList.add(new ProductDto(
                            count,
                            product.getName(),
                            product.getPrice(),
                            product.getStorageDate(),
                            pvtById.getMeassurementType().getName(),
                            pvtById.getMeassurementValue().getValue()
                    ));
                }
            }
            model.addAttribute("listProduct", productDtoList.stream().
                    sorted(Comparator.comparing(ProductDto::getName)).collect(Collectors.toList()));
            return "get";
        }

        model.addAttribute("listProducts", new ArrayList<>());
        return "get";
    }

    public String getProducts(Model model) {
        List<Product> products1 = productRepository.findAll();
        List<Product> products = products1.stream().
                sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        List<ProductDto> productDtoList = new ArrayList<>();
        long count = 0;

        for (Product product : products) {
            count++;
            ProductValueType pvtById = productValueTypeRepository.findByProductId(product.getId());
            productDtoList.add(new ProductDto(
                    count,
                    product.getName(),
                    product.getPrice(),
                    product.getStorageDate(),
                    pvtById.getMeassurementType().getName(),
                    pvtById.getMeassurementValue().getValue()
            ));
        }

        model.addAttribute("listProduct", productDtoList);
        return "get";
    }

    @SneakyThrows
    public String addProduct(HttpServletRequest request) {
        String name = request.getParameter("name");
        String typeOfMeassurement = request.getParameter("TypeOfMeassurement");
        String valeOfMeassurement = request.getParameter("ValeOfMeassurement");

        String storageDate = request.getParameter("storageDate");
        String[] split = storageDate.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));


        String price = request.getParameter("price");

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStorageDate(localDate);
        Product savedProduct = productRepository.save(product);

        ProductValueType productValueType = new ProductValueType();
        productValueType.setProduct(savedProduct);

        MeassurementType savedMT = meassurementTypeRepository.findByName(typeOfMeassurement);
        productValueType.setMeassurementType(savedMT);

        Optional<MeassurementValue> optionalMeassurementValue =
                meassurementValueRepository.findByValue(valeOfMeassurement);
        if (!optionalMeassurementValue.isPresent()) {
            MeassurementValue meassurementValue = new MeassurementValue();
            meassurementValue.setValue(valeOfMeassurement);
            MeassurementValue savedMv = meassurementValueRepository.save(meassurementValue);

            productValueType.setMeassurementValue(savedMv);
        } else {
            productValueType.setMeassurementValue(optionalMeassurementValue.get());
        }

        productValueTypeRepository.save(productValueType);
        return "add";
    }

    public String getDeleteProducts(Model model) {
        List<Product> products1 = productRepository.findAll();
        List<Product> products = products1.stream().
                sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        List<ProductDto> productDtoList = new ArrayList<>();
        long count = 0;

        for (Product product : products) {
            count++;
            ProductValueType pvtById = productValueTypeRepository.findByProductId(product.getId());
            productDtoList.add(new ProductDto(
                    count,
                    product.getName(),
                    product.getPrice(),
                    product.getStorageDate(),
                    pvtById.getMeassurementType().getName(),
                    pvtById.getMeassurementValue().getValue()
            ));
        }

        model.addAttribute("listProduct", productDtoList);
        return "delete";
    }

    public String deleteProduct(String name) {

        Product product = productRepository.findByName(name);

        productValueTypeRepository.deleteByProductId(product.getId());

        productRepository.deleteByName(name);
        return "redirect:/product/delete";
    }

    @SneakyThrows
    public void getFile(String type, HttpServletResponse response) {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductValueType pvtById = productValueTypeRepository.findByProductId(product.getId());
            productDtoList.add(new ProductDto(
                    product.getName(),
                    product.getPrice(),
                    product.getStorageDate(),
                    pvtById.getMeassurementType().getName(),
                    pvtById.getMeassurementValue().getValue()
            ));
        }


        if (type.equalsIgnoreCase("excel")) {
            getFileExcel(productDtoList);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            FileInputStream fileInputStream = new FileInputStream(path + "/products.xlsx");

            response.setHeader("Content-Disposition", "attachment; filename=" +
                    "products");
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        } else {
            getFilePdf(productDtoList);
            response.setContentType("application/pdf");
            FileInputStream fileInputStream = new FileInputStream(path + "/products.pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    "products");
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        }
    }

    @SneakyThrows
    public void getFileExcel(List<ProductDto> productDtoList) {
        File file = new File(path);
        if (!file.exists()) file.mkdir();

        try (FileOutputStream fileOutputStream = new FileOutputStream(path + "/products.xlsx")) {

            List<ProductDto> sortedPDtoList = productDtoList.stream().
                    sorted(Comparator.comparing(ProductDto::getName)).collect(Collectors.toList());

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Products");
            XSSFRow row = sheet.createRow(0);

            row.createCell(0).setCellValue("№");
            row.createCell(1).setCellValue("Name");
            row.createCell(2).setCellValue("Price");
            row.createCell(3).setCellValue("Meassurement value");
            row.createCell(4).setCellValue("Meassurement type");
            row.createCell(5).setCellValue("Storage date");

            int i = 1;

            for (ProductDto productDto : sortedPDtoList) {
                XSSFRow row1 = sheet.createRow(i);
                row1.createCell(0).setCellValue(i);
                row1.createCell(1).setCellValue(productDto.getName());
                row1.createCell(2).setCellValue(productDto.getPrice());
                row1.createCell(3).setCellValue(productDto.getMeassurementValue());
                row1.createCell(4).setCellValue(productDto.getMeassurementType());
                row1.createCell(5).setCellValue(productDto.getStorageDate());
                i++;
            }

            workbook.write(fileOutputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFilePdf(List<ProductDto> productDtoList) {
        File file = new File(path);
        if (!file.exists()) file.mkdir();
        List<ProductDto> sortedPDtoList = productDtoList.stream().
                sorted(Comparator.comparing(ProductDto::getName)).collect(Collectors.toList());

        try (PdfWriter pdfWriter = new PdfWriter(path + "/products.pdf")) {

            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            Paragraph paragraph = new Paragraph("Products");
            document.add(paragraph);
//            float[] columns={2f,2f,10f};
            Table table = new Table(6);

            table.addCell("Number");
            table.addCell("Name");
            table.addCell("Price");
            table.addCell("Meassurement value");
            table.addCell("Meassurement type");
            table.addCell("Storage date");

            int i = 1;

            for (ProductDto productDto : sortedPDtoList) {
                table.addCell(String.valueOf(i));
                table.addCell(productDto.getName());
                table.addCell(productDto.getPrice());
                table.addCell(productDto.getMeassurementValue());
                table.addCell(productDto.getMeassurementType());
                table.addCell(String.valueOf(productDto.getStorageDate()));
                i++;
            }
            document.add(table);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
