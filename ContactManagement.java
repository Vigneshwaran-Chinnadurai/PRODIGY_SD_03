package contactmanagementsystem;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManagement 
{
    // Contact class moved outside of the ContactManagement class
    public static class Contact 
    {
        private String name;
        private String phoneNumber;
        private String email;

        public Contact(String name, String phoneNumber, String email)
        {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        public String getName() 
        {
            return name;
        }

        public void setName(String name) 
        {
            this.name = name;
        }

        public String getPhoneNumber() 
        {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) 
        {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() 
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        @Override
        public String toString() 
        {
            return "Name: " + name + ", Phone: " + phoneNumber + ", Email Id: " + email;
        }
    }
    
    public static class ContactManager 
    {
        private static final String file_name = "contacts.txt";
        private ArrayList<Contact> contacts;

        public ContactManager() 
        {
            contacts = new ArrayList<>();
            loadContacts();
        }

        private void loadContacts() 
        {
            try (BufferedReader br = new BufferedReader(new FileReader(file_name))) 
            {
                String line;
                while ((line = br.readLine()) != null) 
                {
                    String[] data = line.split(",");
                    if (data.length == 3) {
                        contacts.add(new Contact(data[0], data[1], data[2]));
                    }
                }
            } 
            catch (IOException e) 
            {
                System.out.println("Error loading contacts: " + e.getMessage());
            }
        }

        private void saveContacts() 
        {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_name))) 
            {
                for (Contact contact : contacts) 
                {
                    bw.write(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
                    bw.newLine();
                }
            } 
            catch (IOException e) 
            {
                System.out.println("Error saving contacts: " + e.getMessage());
            }
        }

        public void addContact(String name, String phoneNumber, String email)
        {
            contacts.add(new Contact(name, phoneNumber, email));
            saveContacts();
        }

        public void viewContacts()
        {
            if (contacts.isEmpty())
            {
                System.out.println("Contact list is empty.");
            } 
            else
            {
                for (Contact contact : contacts) 
                {
                    System.out.println(contact);
                }
            }
        }

        public void editContact(String name, Scanner scanner)
        {
            for (Contact contact : contacts) 
            {
                if (contact.getName().equalsIgnoreCase(name)) 
                {
                    System.out.println("Enter new phone number:");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.println("Enter new email:");
                    String newEmail = scanner.nextLine();
                    contact.setPhoneNumber(newPhoneNumber);
                    contact.setEmail(newEmail);
                    saveContacts();
                    return;
                }
            }
            System.out.println("Contact not found.");
        }

        public void deleteContact(String name) 
        {
            contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
            saveContacts();
            System.out.println("Contact deleted.");
        }

        public static void main(String[] args)
        {
            ContactManager manager = new ContactManager();
            Scanner scanner = new Scanner(System.in);
            while (true) 
            {
                System.out.println("\nContact Manager:");
                System.out.println("1. Add Contact");
                System.out.println("2. View Contacts");
                System.out.println("3. Edit Contact");
                System.out.println("4. Delete Contact");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) 
                {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter phone number: ");
                        String phoneNumber = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        manager.addContact(name, phoneNumber, email);
                        break;
                    case 2:
                        manager.viewContacts();
                        break;
                    case 3:
                        System.out.print("Enter the name of the contact to edit: ");
                        String editName = scanner.nextLine();
                        manager.editContact(editName, scanner);
                        break;
                    case 4:
                        System.out.print("Enter the name of the contact to delete: ");
                        String deleteName = scanner.nextLine();
                        manager.deleteContact(deleteName);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }
}
