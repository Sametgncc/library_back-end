package com.example.service.business;

import com.example.entity.concretes.business.Borrow;
import com.example.payload.business.ResponseMessage;
import com.example.payload.request.business.BorrowRequest;
import com.example.payload.response.business.BorrowResponse;
import com.example.repository.business.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final BorrowRepository borrowRepository;

    public ResponseMessage<BorrowResponse> createPayments(BorrowRequest borrowRequest) {
        try {
            // Ödünç alınan kitabın bilgilerini kaydet
            Borrow borrow = new Borrow();
            borrow.setBookId(borrowRequest.getBookId());
            borrow.setTitle(borrowRequest.getTitle());
            borrow.setAuthor(borrowRequest.getAuthor());
            borrow.setCategory(borrowRequest.getCategory());
            borrow.setStartDate(borrowRequest.getStartDate());
            borrow.setEndDate(borrowRequest.getEndDate());
            borrowRepository.save(borrow);

            // Sistem tarihini al
            LocalDate today = LocalDate.now();
            LocalDate endDate = borrowRequest.getEndDate();

            // Ceza kontrolü
            long overdueDays = ChronoUnit.DAYS.between(endDate, today);
            double penalty = 0.0;
            if (overdueDays > 0) {
                penalty = overdueDays * 10.0; // Gün başına 10 birim ceza
            }

            // Yanıt oluştur
            BorrowResponse borrowResponse = BorrowResponse.builder()
                    .id(borrow.getId())
                    .bookId(borrow.getBookId())
                    .bookTitle(borrow.getTitle())
                    .bookAuthor(borrow.getAuthor())
                    .bookCategory(borrow.getCategory())
                    .startDate(borrow.getStartDate())
                    .endDate(borrow.getEndDate())
                    .build();

            String message = overdueDays > 0
                    ? "Ceza uygulanmıştır. Toplam ceza: " + penalty + " birim."
                    : "Kitap zamanında teslim edilmiştir.";

            return ResponseMessage.<BorrowResponse>builder()
                    .object(borrowResponse)
                    .message(message)
                    .httpStatus(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return ResponseMessage.<BorrowResponse>builder()
                    .object(null)
                    .message("İşlem sırasında bir hata oluştu: " + e.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
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
                .bookCategory(borrow.getCategory())
                .startDate(borrow.getStartDate())
                .endDate(borrow.getEndDate())
                .build();
    }
}