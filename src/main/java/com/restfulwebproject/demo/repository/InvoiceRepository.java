package com.restfulwebproject.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InvoiceRepository implements  IInvoiceRepository {

    private static final String FIND_ALL_SQL = "select * from invoices";
    private static final String FIND_BY_MONTH_SQL = "select * from invoices where date_part('month', date) = ?";
    private static final String COUNT_SQL = "select count(*) from invoices";
    private static final String FIND_BY_ID_SQL = "select * from invoices where id = ?";
    private static final String FIND_BY_DATE_SQL = "select * from invoices where date = ?";
    private static final String SAVE_SQL = "insert into invoices (name, address, date, total) values (:name, :address, :date, :total)";



    private static final Vector<Invoice> ms_invoices =new Vector();
   private static int m_currentId;


    private final JdbcTemplate m_jdbcTemplate;
    @Autowired
    public InvoiceRepository(JdbcTemplate m_jdbcTemplate)
    {
        this.m_jdbcTemplate = m_jdbcTemplate;
    }

    private void fillInvoices(ResultSet resultSet, List<Invoice> invoices) throws SQLException
    {
        do {
            var invoiceId =  resultSet.getInt(1); //resultSet.getInt("invoice_id");
            var name = resultSet.getString(2);
            var address = resultSet.getString(3);
            var date = resultSet.getDate(4).toLocalDate();
            var total = resultSet.getDouble(5);

            invoices.add(new Invoice(invoiceId, name, address, date, BigDecimal.valueOf(total)));
        } while (resultSet.next());
    }

    Object[] getArguments(Object... args) {
        return args;
    }

    @Override
    public Iterable<Invoice> findInvoicesByMonth(int month)
    {
        var invoices = new ArrayList<Invoice>();
        Integer mon[]={Integer.valueOf(month)};

        m_jdbcTemplate.query(FIND_BY_MONTH_SQL, getArguments(month), (ResultSet rs) -> fillInvoices(rs, invoices));

        return invoices;
    }

    @Override
    public Iterable<Invoice> findInvoicesByDate(LocalDate date)
    {
        var invoices = new ArrayList<Invoice>();

      LocalDate dates[]={date};

        m_jdbcTemplate.query(FIND_BY_DATE_SQL, dates, (ResultSet rs) -> fillInvoices(rs, invoices));

        return invoices;
    }

    @Override
    public long count() {
        return ms_invoices.size();
    }

    @Override
    public void delete(Invoice entity) {

       ms_invoices.remove(0);

    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void delete(Iterable<? extends Invoice> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existById(Integer integer) {

        return findById(integer).isPresent();
    }

    @Override
    public Iterable<Invoice> findAll()
    {
        var invoices = new ArrayList<Invoice>();


        m_jdbcTemplate.query("select * from invoices", ( ResultSet rs) -> fillInvoices(rs, invoices));

        return invoices;
    }

    @Override
    public Optional<Invoice> findById(Integer id) {
        var invoices=new ArrayList<Invoice>();
        Integer Ints[]={id};
        m_jdbcTemplate.query(FIND_BY_ID_SQL,Ints,( ResultSet rs) -> fillInvoices(rs, invoices));

        return invoices.stream().findFirst();
    }

    @Override
    public <E extends Invoice> E save(E invoiceTobeSave) {

         invoiceTobeSave.setId(++m_currentId);
         ms_invoices.add(invoiceTobeSave);
         return invoiceTobeSave;
    }

    @Override
    public <E extends Invoice> E saveAll(Iterable<E> entity)
    {
          throw new UnsupportedOperationException();
    }
}
