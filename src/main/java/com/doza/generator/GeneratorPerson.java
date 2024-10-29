package com.doza.generator;

import com.doza.dao.PersonDao;
import com.doza.entity.Person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class GeneratorPerson {

    private static final String[] NAMES = {
            "John", "Alice", "Michael", "Sophia", "James", "Emma", "William", "Olivia", "David", "Isabella",
            "Ethan", "Mia", "Alexander", "Ava", "Daniel", "Charlotte", "Henry", "Amelia", "Matthew", "Harper",
            "Samuel", "Evelyn", "Joseph", "Abigail", "Elijah", "Emily", "Noah", "Grace", "Liam", "Chloe",
            "Lucas", "Ella", "Benjamin", "Scarlett", "Mason", "Lily", "Sebastian", "Hannah", "Jacob", "Zoe",
            "Jack", "Victoria", "Logan", "Sofia", "Oliver", "Aria", "Leo", "Madison", "Nathan", "Layla"
    };

    private static final String[] SURNAME = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Martinez", "Hernandez",
            "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee",
            "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker",
            "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores", "Green", "Adams",
            "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts", "Gomez", "Phillips"
    };

    private static final String[] EMAIL = {
            "@gmail.com", "@yandex.ru", "@mail.ru", "@google.com", "@yahoo.com", "@test.com", "@test.ru",
            "@outlook.com", "@icloud.com", "@hotmail.com", "@aol.com", "@protonmail.com", "@zoho.com", "@gmx.com",
            "@yandex.com", "@mail.com", "@fastmail.com", "@inbox.com", "@me.com", "@live.com", "@msn.com",
            "@tutanota.com", "@rediffmail.com", "@btinternet.com", "@virginmedia.com", "@comcast.net",
            "@verizon.net", "@sbcglobal.net", "@sky.com", "@att.net", "@optonline.net", "@charter.net",
            "@cox.net", "@earthlink.net", "@bellsouth.net", "@shaw.ca", "@rogers.com", "@telus.net",
            "@centurylink.net", "@frontier.com", "@q.com", "@vodafone.com", "@o2.com", "@orange.fr",
            "@tiscali.co.uk", "@freenet.de", "@bluewin.ch", "@laposte.net", "@neuf.fr", "@wanadoo.fr",
            "@telefonica.net", "@virgilio.it"
    };

    private static final String[] PASSWORDS = {
            "P@ssw0rd123", "Secure!Password1", "MyS3cret#Code", "1LoveJava@", "R3ad&Write!",
            "C0mpl3x!P@ss", "1ncred!ble#H@ck", "C0ding#IsFun", "H@ppyD@y2024", "B3autiful*Life",
            "1l0veC0ding$", "S@fe!AndS0und", "Br@in1ac$", "Java!Rocks2024", "0ptim!stic*Mind",
            "C0deF0rLife#", "MyN3wP@ssword", "S3cur3P@ss!2024", "W0nderful*Day", "Ch@llenge#Accepted",
            "SecreT1234!", "R3liab!lity&Trust", "Gr3at@Times", "Innov@tive2024", "1nvent!on$F@ctory",
            "Fr33Dom!R!de", "G00dLuck&Success", "H0pe&Dre@m$2024", "Br@ve!NewW0rld", "Qu!ckF!x1234",
            "N3xtG3n!0n$", "Sm@rtT3ch&Tr@in", "C0d3B@se@123", "N3verG!veUp!", "St@yStrong@24",
            "S@feH@ven$2024", "Excell3nt*Ideas", "Dr3amBig!2024", "S@fetyFirst*2024", "M0tivation#4U",
            "C0nfid3nce!Boost", "J@vaF!rst*", "G0BigOrG0H0me@", "S3nseOfHumor!", "L1feIsF@ntastic",
            "M@keItH@ppen2024", "BelieveInY0urself!", "S1mpl3!B3auty", "P@ssw0rd!R3volution", "1Ch@llenge1",
    };


    public void createOneHundredPerson() {
        for (int i = 0; i < 100; i++) {
            PersonDao personDao = PersonDao.getInstance();
            Person person = new Person();
            person.setFirstName(generateRandomPersonName());
            person.setLastName(generateRandomPersonSurname());
            person.setEmail(generateRandomPersonEmail());
            person.setPassword(generateRandomPassword());
            person.setDateOfBirth(generateRandomUserDateOfBirth());

            Person savedPerson = personDao.save(person);
            System.out.println(savedPerson);
        }
    }


    private static String generateRandomPersonName() {
        Random random = new Random();
        return NAMES[random.nextInt(NAMES.length)];
    }

    private static String generateRandomPersonSurname() {
        Random random = new Random();
        return SURNAME[random.nextInt(SURNAME.length)];
    }

    private static String generateRandomPersonEmail() {
        Random random = new Random();
        return generateRandomPersonName() + generateRandomPersonSurname() + EMAIL[random.nextInt(EMAIL.length)];
    }

    private static String generateRandomPassword() {
        Random random = new Random();
        return PASSWORDS[random.nextInt(PASSWORDS.length)];
    }

    private static LocalDate generateRandomUserDateOfBirth() {
        // Устанавливаем границы: с 1950 года по сегодняшний день
        LocalDate startDate = LocalDate.of(1950, 1, 1);
        LocalDate endDate = LocalDate.now();
        // Количество дней между начальной и конечной датами
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        // Генерация случайного числа дней в пределах этого диапазона
        Random random = new Random();
        long randomDays = random.nextLong() % daysBetween;
        // Возвращаем случайную дату
        return startDate.plusDays(randomDays);
    }
}
