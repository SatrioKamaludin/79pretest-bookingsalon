package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Person;
import com.booking.models.Employee;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class PrintService {
    public static void printMenu(String title, String[] menuArr) {
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);
            num++;
        }
    }

    public static void printServices(List<Service> serviceList) { // was String but now void
        int num = 1;
        // Bisa disesuaikan kembali
        System.out.printf("| %-4s | %-7s | %-20s | %-9s |\n",
                "No.", "ID", "Nama", "Harga");
        System.out
                .println("+===================================================+");
        for (Service service : serviceList) {
            System.out.printf("| %-4s | %-7s | %-20s | %-9s |\n",
                    num, service.getServiceId(), service.getServiceName(), service.getPrice());
            num++;
        }
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public static void showRecentReservation(List<Reservation> reservationList) {
        int num = 1;
        System.out.printf("| %-4s | %-7s | %-14s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out
                .println(
                        "+================================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equals("In Process")) {
                String customerName = reservation.getCustomer().getName();
                String serviceName = getSelectedServiceNames(reservation.getServices());
                double serviceCost = reservation.getReservationPrice();
                String employeeName = reservation.getEmployee().getName();
                String workstage = reservation.getWorkstage();
                System.out.printf("| %-4d | %-7s | %-14s | %-15s | %-15.2f | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), customerName, serviceName, serviceCost, employeeName,
                        workstage);
                num++;
            }
        }
        System.out
                .println(
                        "+================================================================================================+");
    }

    public static String getSelectedServiceNames(List<Service> services) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < services.size(); i++) {
            sb.append(services.get(i).getServiceName());
            if (i < services.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static void showAllCustomer(List<Person> personList) {
        int num = 1;
        System.out.printf("| %-4s | %-7s | %-11s | %-12s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Alamat", "Membership", "Wallet");
        System.out
                .println("+========================================================================================+");
        for (Person person : personList) {
            if (person instanceof Customer) {
                System.out.printf("| %-4s | %-7s | %-11s | %-12s | %-15s | %-10s |\n",
                        num, person.getId(), person.getName(), person.getAddress(),
                        ((Customer) person).getMember().getMembershipName(),
                        ((Customer) person).getWallet());
                num++;
            }
        }
        System.out
                .println("+========================================================================================+");
    }

    public static void showAllEmployee(List<Person> personList) {
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-12s | %-10s |\n",
                "No.", "ID", "Nama Employee", "Alamat", "Experience");
        System.out
                .println("+=========================================================+");
        for (Person person : personList) {
            if (person instanceof Employee) {
                System.out.printf("| %-4s | %-4s | %-11s | %-12s | %-10s |\n",
                        num, person.getId(), person.getName(), person.getAddress(),
                        ((Employee) person).getExperience());
                num++;
            }
        }
        System.out
                .println("+=========================================================+");
    }

    public static void showHistoryReservation(List<Reservation> reservationList) {
        int num = 1;
        double totalProfit = 0.0;
        System.out.printf("| %-4s | %-7s | %-14s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out
                .println(
                        "+================================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equals("Finished") || reservation.getWorkstage().equals("Cancelled")) {
                String customerName = reservation.getCustomer().getName();
                String serviceName = getSelectedServiceNames(reservation.getServices());
                double serviceCost = reservation.getReservationPrice();
                String employeeName = reservation.getEmployee().getName();
                String workstage = reservation.getWorkstage();
                System.out.printf("| %-4d | %-7s | %-14s | %-15s | %-15.2f | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), customerName, serviceName, serviceCost, employeeName,
                        workstage);
                num++;

                if (reservation.getWorkstage().equals("Finished")) {
                    totalProfit += serviceCost;
                }
            }

        }
        // only sum total profit reservations where getWorkstage = Finished

        System.out.println("Total Keuntungan: Rp." + totalProfit + "-");
    }
}
