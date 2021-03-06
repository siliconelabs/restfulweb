package com.restfulwebproject.demo.Servicelayer;

import com.restfulwebproject.demo.Controllers.InvoiceController;
import com.restfulwebproject.demo.converter.InvoiceConverter;
import com.restfulwebproject.demo.dto.InvoiceDTO;
import com.restfulwebproject.demo.repository.IInvoiceRepository;
import com.restfulwebproject.demo.repository.Invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServiceInvoice {

    private final IInvoiceRepository m_invoiceRepository;
    private final InvoiceConverter invoiceConverter;


    @Autowired
    public ServiceInvoice(IInvoiceRepository m_invoiceRepository,InvoiceConverter invoiceConverter) {
        this.m_invoiceRepository = m_invoiceRepository;
        this.invoiceConverter = invoiceConverter;
    }

    public Invoice saveInvoce(InvoiceDTO invoiceDTO)
    {
        try
        {
            return m_invoiceRepository.save(invoiceConverter.convertfromDTOtoInvoice(invoiceDTO));
        }
        catch(Throwable ex)
        {
            throw new RuntimeException(ex.getMessage());
        }
    }
    private Iterable<InvoiceDTO> getInvoiceDTOs(Iterable<Invoice> invoices)
    {
        return StreamSupport.stream(invoices.spliterator(), false)
                .map(invoiceConverter::convertTODTO)
                .collect(Collectors.toList());
    }

    public Iterable<InvoiceDTO> findALlInvoices()
    {

        return getInvoiceDTOs(m_invoiceRepository.findAll());
    }

    public Iterable<Invoice> findByMonth(int mon)
    {
        return m_invoiceRepository.findInvoicesByMonth(mon);
    }

    public  Iterable<Invoice> findByDate(LocalDate date)
    {
        return m_invoiceRepository.findInvoicesByDate(date);
    }


    public boolean deleteInvoice(int id) {
        Optional<Invoice> invoiceDb = m_invoiceRepository.findById(id);

        if(invoiceDb.isPresent()) {
            m_invoiceRepository.delete(invoiceDb.get());
            return true;
        }else {
            return false;
            //throw new RuntimeException("not found");
        }

    }

    public Invoice  updateInvoice(Invoice invoice)
    {

        Optional<Invoice> invoiceDb = m_invoiceRepository.findById(invoice.getId());

        if(invoiceDb.isPresent()) {
            Invoice productUpdate = invoiceDb.get();
            productUpdate.setId(invoice.getId());
            productUpdate.setName(invoice.getName());
            productUpdate.setTotal(invoice.getTotal());

            m_invoiceRepository.save(productUpdate);
            return productUpdate;
        }else {

        }

        return   m_invoiceRepository.save(invoice);
    }

}
