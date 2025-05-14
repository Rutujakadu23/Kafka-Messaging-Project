package com.example.otp.util;

import com.example.otp.dto.BuyerDTO;
import com.example.otp.dto.OrderItemDTO;
import com.example.otp.dto.OrderWithBuyerDTO;
import com.example.otp.dto.ProductDTO;
import com.example.otp.dto.SupplierDTO;  // Add this import
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;

public class InvoicePdfGenerator {

    public static void generateInvoice(OutputStream outputStream, OrderWithBuyerDTO orderDto) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Paragraph title = new Paragraph("Invoice", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        // Order Info
        document.add(new Paragraph("Order ID: " + orderDto.getOrderId()));
        document.add(new Paragraph("Order Date: " + orderDto.getOrderDate()));
        document.add(new Paragraph("Total Amount: ₹" + orderDto.getTotalPrice()));
        document.add(new Paragraph(" "));

        // Buyer Info
        document.add(new Paragraph("Buyer: " + orderDto.getBuyerName()));
        document.add(new Paragraph("Phone: " + orderDto.getBuyerPhone()));
        document.add(new Paragraph("Address: " + orderDto.getDeliveryDate()));  // Assuming deliveryDate is the address (adjust as needed)
        document.add(new Paragraph(" "));

        // Table Header
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.addCell("Product");
        table.addCell("Price");
        table.addCell("Quantity");
        table.addCell("Supplier");
        table.addCell("Supplier Phone");

        // Table Data (Iterate over OrderItems)
        for (OrderItemDTO orderItemDTO : orderDto.getOrderItems()) {
            ProductDTO product = orderItemDTO.getProduct();
            SupplierDTO supplier = product.getSupplier();  // Get SupplierDTO from ProductDTO

            // Add Product Details to the table
            table.addCell(product.getName());
            table.addCell("₹" + product.getPrice());
            table.addCell(String.valueOf(orderItemDTO.getQuantity()));

            // Access Supplier Details from SupplierDTO
            table.addCell(supplier != null ? supplier.getEmail() : "N/A");  // Adjust as needed (e.g., supplier.getEmail() or supplier.getPhone())
            table.addCell(supplier != null ? supplier.getPhone() : "N/A");  // Adjust if you need supplier phone or email
        }

        document.add(table);

        // Close the document
        document.close();
    }
}
