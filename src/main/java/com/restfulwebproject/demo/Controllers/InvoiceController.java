package com.restfulwebproject.demo.Controllers;

import com.restfulwebproject.demo.Servicelayer.ServiceInvoice;
import com.restfulwebproject.demo.dto.InvoiceDTO;
import com.restfulwebproject.demo.repository.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/Invoices")
public class InvoiceController {

    private final ServiceInvoice service;

    @Autowired
    public InvoiceController(ServiceInvoice service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public Iterable<InvoiceDTO> getAllInvoices()
    {
       return  service.findALlInvoices();
    }

    @GetMapping("/invoicemonth")
    public Iterable<Invoice> getInvoicesByMonth(@RequestParam(value = "mon", required = false, defaultValue = "0") int month)
    {
        if (month < 0) //Bu kısım service bırakılabilir
        {
            System.out.println("böyle bir ay yok");

        }

        return service.findByMonth(month);
    }

    @GetMapping("/invoicedate")//http://localhost:8080/api/invoices/invoicedate?day=5&mon=6&year=2020
    public Iterable<Invoice> getInvoicesByDateInfo(@RequestParam(value = "day") int day, @RequestParam(value = "mon") int month,
            @RequestParam(value = "year", required = false, defaultValue = "0") int year)
    {
        if (year <= 0)
            year = LocalDate.now().getYear();

        return service.findByDate(LocalDate.of(year, month, day));
    }

    @PostMapping("/save")
    public Invoice saveInvoice(@RequestBody InvoiceDTO invoiceDTO)
    {
        return service.saveInvoce(invoiceDTO);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteProduct(@PathVariable int id){
        boolean result =service.deleteInvoice(id);
        return result ? HttpStatus.OK: HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Invoice> updateProduct(@PathVariable long id, @RequestBody Invoice invoice){
        service.updateInvoice(invoice);
        return ResponseEntity.ok().body(service.updateInvoice(invoice));
    }





}
