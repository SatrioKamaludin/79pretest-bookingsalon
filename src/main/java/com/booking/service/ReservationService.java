package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Membership;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class ReservationService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static PersonRepository personRepository = new PersonRepository();
    private static ServiceRepository serviceRepository = new ServiceRepository();

    public static void createReservation() {
        Scanner input = new Scanner(System.in);

        String reservationId = generateReservationId();

        PrintService.showAllCustomer(personList);
        String customerId;
        boolean validCustomer = false;
        do {
            System.out.print("Silahkan Masukkan Customer ID: ");
            customerId = input.nextLine();
            validCustomer = validateCustomerId(customerId);
            if (!validCustomer) {
                System.out.println("Customer yang dicari tidak tersedia.");
            }
        } while (!validCustomer);

        PrintService.showAllEmployee(personList);
        String employeeId;
        boolean validEmployee = false;
        do {
            System.out.print("Silahkan Masukkan Employee ID: ");
            employeeId = input.nextLine();
            validEmployee = validateEmployeeId(employeeId);
            if (!validEmployee) {
                System.out.println("Employee yang dicari tidak tersedia.");
            }
        } while (!validEmployee);

        PrintService.printServices(serviceList);
        List<Service> selectedServices = new ArrayList<>();
        String addServices;
        do {
            System.out.print("Silahkan Masukkan Service ID: ");
            String serviceId = input.nextLine();
            if (!validateServiceId(serviceId)) {
                System.out.println("Service yang dicari tidak tersedia.");
            } else {
                boolean serviceAlreadyChoosen = selectedServices.stream()
                        .anyMatch(service -> service.getServiceId().equals(serviceId));
                if (serviceAlreadyChoosen) {
                    System.out.println("Service sudah dipilih");
                } else {
                    Service selectedService = serviceList.stream()
                            .filter(service -> service.getServiceId().equals(serviceId))
                            .findFirst()
                            .orElse(null);
                    selectedServices.add(selectedService);
                }
            }
            System.out.println("Ingin pilih service yang lain (Y/T)? ");
            addServices = input.nextLine();
        } while (addServices.equalsIgnoreCase("Y"));

        double reservationPrice = calculateReservationPrice(selectedServices, customerId);
        Reservation reservation = new Reservation(reservationId, (Customer) getPersonById(customerId),
                (Employee) getPersonById(employeeId), selectedServices, reservationPrice, "In Process");
        reservationList.add(reservation);
        PrintService.showRecentReservation(reservationList);
        System.out.println("Booking berhasil");
        System.out.println("Total Biaya Booking: Rp. " + reservationPrice);
    }

    public static List<Reservation> getReservationList() {
        return reservationList;
    }

    private static boolean validateCustomerId(String customerId) {
        List<Person> persons = personRepository.getAllPerson();
        for (Person person : persons) {
            if (person instanceof Customer && person.getId().equals(customerId)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateEmployeeId(String employeeId) {
        List<Person> persons = personRepository.getAllPerson();
        for (Person person : persons) {
            if (person instanceof Employee && person.getId().equals(employeeId)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateServiceId(String serviceId) {
        List<Service> services = serviceRepository.getAllService();
        for (Service service : services) {
            if (service.getServiceId().equals(serviceId)) {
                return true;
            }
        }
        return false;
    }

    // public static void getCustomerByCustomerId() {

    // }

    public static void editReservationWorkstage() {
        Scanner input = new Scanner(System.in);
        PrintService.showRecentReservation(reservationList);
        System.out.print("Silahkan Masukkan Reservation Id: ");
        String reservatonId = input.nextLine();

        Reservation setReservationStatus = getReservationById(reservatonId);
        if (setReservationStatus != null) {
            System.out.print("Selesaikan reservasi (Finish/Cancel): ");
            String status = input.nextLine();

            if (status.equalsIgnoreCase("Finish")) {
                setReservationStatus.setWorkstage("Finished");
                System.out.println("Reservasi dengan id " + reservatonId + " sudah Finish");
            } else if (status.equalsIgnoreCase("Cancel")) {
                setReservationStatus.setWorkstage("Cancelled");
                System.out.println("Reservasi dengan id " + reservatonId + " sudah Cancel");
            } else {
                System.out.println("Status tidak valid");
            }
        } else {
            System.out.println("Reservasi dengan id " + reservatonId + " tidak ditemukan");
        }

    }

    private static int reservationNumber = 1;

    public static String generateReservationId() {
        return "Rsv-" + String.format("%02d", reservationNumber++);
    }

    public static Reservation getReservationById(String reservationId) {
        return reservationList.stream()
                .filter(reservation -> reservation.getReservationId().equals(reservationId))
                .findFirst()
                .orElse(null);
    }

    public static Person getPersonById(String id) {
        return personList.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static double calculateReservationPrice(List<Service> selectedServices, String customerId) {
        double totalPrice = selectedServices.stream().mapToDouble(Service::getPrice).sum();

        Customer customer = (Customer) getPersonById(customerId);
        if (customer != null) {
            Membership membership = customer.getMember();
            if (membership != null) {
                String membershipName = membership.getMembershipName();
                if (membershipName.equalsIgnoreCase("Silver")) {
                    totalPrice *= 0.95;
                } else if (membershipName.equalsIgnoreCase("Gold")) {
                    totalPrice *= 0.9;
                }
            }
        }
        return totalPrice;
    }

    // Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan
}
