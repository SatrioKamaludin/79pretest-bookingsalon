package com.booking.service;

import java.util.List;
import java.util.Scanner;

import com.booking.models.Customer;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;
import com.booking.service.PrintService;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = { "Show Data", "Create Reservation", "Complete/cancel reservation", "Exit" };
        String[] subMenuArr = { "Recent Reservation", "Show Customer", "Show Available Employee", "Show services",
                "Show Reservation History and Total profit", "Back to main menu" };

        int optionMainMenu;
        int optionSubMenu;

        boolean backToMainMenu = false;
        boolean backToSubMenu = false;

        List<Reservation> reservations = null;

        do {
            PrintService.printMenu("Main Menu", mainMenuArr);
            optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    do {
                        PrintService.printMenu("Show Data", subMenuArr);
                        optionSubMenu = Integer.valueOf(input.nextLine());
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                // panggil fitur tampilkan recent reservation
                                if (reservations == null) {
                                    reservations = ReservationService.getReservationList();
                                }
                                PrintService.showRecentReservation(reservations);
                                break;
                            case 2:
                                // panggil fitur tampilkan semua customer
                                PrintService.showAllCustomer(personList);
                                break;
                            case 3:
                                // panggil fitur tampilkan semua employee
                                PrintService.showAllEmployee(personList);
                                break;
                            case 4:
                                // panggil fitur tampilkan services salon
                                PrintService.printServices(serviceList);
                                break;
                            case 5:
                                // panggil fitur tampilkan history reservation + total keuntungan
                                if (reservations == null) {
                                    reservations = ReservationService.getReservationList();
                                }
                                PrintService.showHistoryReservation(reservations);
                                break;
                            case 0:
                                // return to optionMainMenu
                                backToSubMenu = true;
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    // panggil fitur menambahkan reservation
                    ReservationService.createReservation();
                    reservations = null;
                    break;
                case 3:
                    // panggil fitur mengubah workstage menjadi finish/cancel
                    ReservationService.editReservationWorkstage();
                    reservations = null;
                    break;
                case 0:
                    // terminate program
                    System.out.println("See you again!!!");
                    System.exit(0);
                default:
                    System.out.println("Inputan anda salah");
            }
        } while (!backToMainMenu);

    }
}
