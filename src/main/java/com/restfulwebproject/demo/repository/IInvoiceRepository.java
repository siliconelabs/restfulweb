package com.restfulwebproject.demo.repository;

import java.time.LocalDate;

public interface IInvoiceRepository  extends ICrudRepository<Invoice,Integer>{
    Iterable<Invoice> findInvoicesByMonth(int month);
    Iterable<Invoice> findInvoicesByDate(LocalDate date);
}
