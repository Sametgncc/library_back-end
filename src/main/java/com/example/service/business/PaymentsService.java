package com.example.service.business;

import com.example.entity.concretes.business.Borrow;
import com.example.entity.concretes.business.Payments;
import com.example.payload.request.business.BorrowRequest;
import com.example.payload.response.business.BorrowResponse;
import com.example.payload.response.business.PaymentsResponse;
import com.example.repository.business.BorrowRepository;
import com.example.repository.business.PaymentsRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final BorrowRepository borrowRepository;
    private final PaymentsRepository paymentsRepository;

    public PaymentsResponse createPayments(BorrowRequest borrowRequest) {
        // BorrowRequest'ten gerekli bilgileri al
        String title = borrowRequest.getTitle();
        String author = borrowRequest.getAuthor();
        LocalDate endDate = borrowRequest.getEndDate();

        // Sistem tarihini al
        LocalDate today = LocalDate.now();

        // Gecikme gün sayısını hesapla
        long overdueDays = ChronoUnit.DAYS.between(endDate, today);

        // Ceza hesaplama (gün başına 10 birim)
        long debtAmount = overdueDays > 0 ? overdueDays * 10 : 0;

        // Ödeme bilgilerini kaydet
        Payments payment = Payments.builder()
                .title(title)
                .author(author)
                .daysLate(overdueDays > 0 ? overdueDays : 0)
                .debtAmount(debtAmount)
                .build();
        paymentsRepository.save(payment);

        // Yanıt oluştur
        return PaymentsResponse.builder()
                .id(payment.getId())
                .title(payment.getTitle())
                .author(payment.getAuthor())
                .daysLate(payment.getDaysLate())
                .debtAmount(payment.getDebtAmount())
                .build();
    }

    // Tüm ödünç alınan kitapları getir
    public List<BorrowResponse> getAllBorrows() {
        return borrowRepository.findAll().stream()
                .map(this::mapToBorrowResponse)
                .collect(Collectors.toList());
    }

    // Borrow nesnesini BorrowResponse'a dönüştür
    private BorrowResponse mapToBorrowResponse(Borrow borrow) {
        return BorrowResponse.builder()
                .id(borrow.getId())
                .bookId(borrow.getBookId())
                .bookTitle(borrow.getTitle())
                .bookAuthor(borrow.getAuthor())
                .startDate(borrow.getStartDate())
                .endDate(borrow.getEndDate())
                .build();
    }

    public void addOverduePayments() {
        LocalDate today = LocalDate.now();
        List<Borrow> overdueBorrows = borrowRepository.findAll().stream()
                .filter(borrow -> borrow.getEndDate().isBefore(today))
                .collect(Collectors.toList());

        for (Borrow borrow : overdueBorrows) {
            long overdueDays = ChronoUnit.DAYS.between(borrow.getEndDate(), today);
            long debtAmount = overdueDays * 10; // Gün başına 10 birim ceza

            Payments payment = Payments.builder()
                    .title(borrow.getTitle())
                    .author(borrow.getAuthor())
                    .daysLate(overdueDays)
                    .debtAmount(debtAmount)
                    .build();

            paymentsRepository.save(payment);
        }
    }
}