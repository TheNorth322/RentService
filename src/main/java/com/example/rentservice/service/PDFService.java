package com.example.rentservice.service;

import com.example.rentservice.entity.agreement.AgreementEntity;
import com.example.rentservice.entity.agreement.AgreementRoomEntity;
import com.example.rentservice.entity.user.EntityUserEntity;
import com.example.rentservice.entity.user.IndividualUserEntity;
import com.example.rentservice.entity.user.PassportEntity;
import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.repository.AgreementRoomRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class PDFService {
    @Autowired
    private AgreementRoomRepository agreementRoomRepository;

    public byte[] generateAgreementPdf(AgreementEntity agreement) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType0Font.load(document, new FileInputStream("c:/windows/fonts/arial.ttf"), false), 12);
                contentStream.beginText();

                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Информация о договоре:");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Регистрационный номер: " + agreement.getRegistrationNumber());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Частота оплаты: " + agreement.getPaymentFrequency());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Начало действия договора: " + agreement.getStartsFrom());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Окончание действия договора: " + agreement.getLastsTo());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Штраф: " + agreement.getFine());
                contentStream.newLineAtOffset(0, -15);

                contentStream.newLineAtOffset(0, -30);
                contentStream.showText("Информация о пользователе:");

                UserEntity user = agreement.getUser();
                if (user != null) {
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Имя пользователя: " + user.getUsername());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Email пользователя: " + user.getEmail());

                    // Дополнительная информация в зависимости от типа пользователя
                    if (user.getIndividualUser() != null) {
                        IndividualUserEntity individualUser = user.getIndividualUser();
                        PassportEntity passport = individualUser.getActivePassport();
                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText("ФИО пользователя: " + passport.getFirstName() + " " + passport.getLastName() + ((passport.getSurname() == null) ? "" : " " + passport.getSurname()));
                    } else if (user.getEntityUser() != null) {
                        EntityUserEntity entityUser = user.getEntityUser();
                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText("Наименование организации: " + entityUser.getName());
                    }
                }

                // Добавляем таблицу с информацией о комнатах
                contentStream.newLineAtOffset(0, -30);
                contentStream.showText("Информация о комнатах:");

                List<AgreementRoomEntity> agreementRooms = agreementRoomRepository.findAllByAgreement_Id(agreement.getId());
                if (agreementRooms != null && !agreementRooms.isEmpty()) {
                    int lineNumber = 1;

                    for (AgreementRoomEntity agreementRoom : agreementRooms) {
                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText(String.format(
                                "%d. Адрес: %s", lineNumber++,
                                agreementRoom.getRoom().getBuilding().getAddress().getName()
                        ));

                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText(String.format(
                                "   Цель аренды: %s", agreementRoom.getPurposeOfRent()
                        ));

                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText(String.format(
                                "   Начало аренды: %s", agreementRoom.getStartOfRent()
                        ));

                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText(String.format(
                                "   Конец аренды: %s", agreementRoom.getEndOfRent()
                        ));

                        contentStream.newLineAtOffset(0, -15);
                        contentStream.showText(String.format(
                                "   Сумма аренды: %s", agreementRoom.getRentAmount()
                        ));
                    }
                }

                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }
}
