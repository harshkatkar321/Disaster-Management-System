

# 🌪️ Disaster Management System (DMS)

## 📌 Project Synopsis

### 🧠 **Problem Statement**

In times of natural calamities such as floods, earthquakes, and cyclones, government agencies and local authorities often face:

* Disorganized communication channels
* Fragmented data on affected zones and available relief resources
* Delays in reaching out to citizens in need
* Poor visibility into real-time disaster updates

These challenges hamper timely rescue, relief, and rehabilitation efforts.

To address this, we designed a centralized, scalable, and responsive **Disaster Management System (DMS)** to streamline disaster handling and improve operational coordination between authorities, volunteers, and citizens.


### 💡 **Our Solution**

We developed a full-stack **Disaster Management System** that:

* Allows **administrators** to register and monitor disasters in real time
* Enables **volunteers** and relief organizations to register available resources (shelter, food, medical aid)
* Sends **email alerts** to affected citizens via an SMTP-based notification system
* Displays an **interactive map** for disaster zones and available relief centers
* Implements **role-based access control** for Admin, Volunteer, and Citizen users
* Maintains detailed logs and disaster data through a persistent, secure backend

## 🛠️ Technologies Used

| Layer            | Tech Stack                                |
| ---------------- | ----------------------------------------- |
| 🧠 Backend       | Spring Boot, Spring MVC, Spring Data JPA  |
| 💾 Database      | MySQL                                     |
| 📬 Notifications | SMTP Email (JavaMailSender)               |
| 🗺️ Maps         | LeafletJS (front-end), integrated via API |
| 🔒 Security      | Spring Security                           |
| 📦 Build Tool    | Maven                                     |
| 🌐 API           | RESTful APIs                              |


## 🌱 Key Spring Features Implemented

| Spring Feature             | Usage                                          |
| -------------------------- | ---------------------------------------------- |
| **Spring Boot**            | Auto-configuration and rapid application setup |
| **Spring MVC**             | RESTful controller endpoints for APIs          |
| **Spring Data JPA**        | ORM for database operations                    |
| **Spring Security**        | Role-based login and authorization             |
| **Spring Validation**      | Bean and form-level validations                |
| **JavaMailSender (SMTP)**  | Email alert system for registered citizens     |
| **application.properties** | Environment-based configurations               |


## ⚙️ System Modules

### 1. **Disaster Module**

* Admins can create, update, and close disaster records
* Attach disaster location (lat/long) to visualize on maps

### 2. **User Management**

* Role-based login (Admin, Volunteer, Citizen)
* Admin manages users and their permissions

### 3. **Resource Module**

* Volunteers can register and offer resources
* Admin assigns resources to disaster zones as needed

### 4. **Email Notification System**

* Citizens are notified via email during new disasters
* Admins can trigger broadcast emails through SMTP integration

### 5. **Map Integration**

* Displays disaster-affected zones and nearby shelters
* LeafletJS integration with backend location data


## 🔐 Note on Secret Keys

This project **previously included SMS alert integration using Twilio**, which has been removed due to security policies and repository protection rules.

If you wish to re-enable SMS features:

* You must **configure your own Twilio credentials** in `application.properties` or through environment variables.
* Visit the official Twilio Console to generate your keys:

👉 [Get Twilio Account SID and Auth Token](https://www.twilio.com/console)
👉 [Twilio SMS API Docs](https://www.twilio.com/docs/sms/send-messages)

⚠️ **Never commit your credentials directly into the codebase**. Use `.env` files or externalized config and add them to `.gitignore`.


## 🚀 How We Handled the Challenge

* Structured the project into **modular layers**: `Controller`, `Service`, `Repository`, and `Entity`
* Implemented **centralized error handling** using `@ControllerAdvice`
* Used **Spring Security** for managing user roles and protected endpoints
* Managed sensitive credentials via `application.properties` and environment variables
* Designed RESTful APIs for seamless frontend-backend integration
* Integrated SMTP service using `JavaMailSender` for scalable and automated communication

## 📌 Future Enhancements

* Real-time data feed with WebSocket integration
* Integration with public disaster APIs (e.g., NASA EONET, GDACS)
* Mobile-first UI and offline access support
* Multilingual support for regional users
* Analytics dashboard for disaster impact and response tracking


## 🤝 Team Credits

* **Harsh Katkar** – Front-end Developer *(Team Lead)*
* **Balaji Pawar** – Backend Developer
* **Pratik Kamthe** – UI/UX Designer
* **Viraj Bhosle** – Frontend Developer


## 🧾 How to Run the Project

# Clone the repository
git clone https://github.com/harshkatkar321/Disaster-Management-System.git

# Import as Maven Project in Spring Tool Suite (STS)

# Update database and SMTP email configurations in:
src/main/resources/application.properties

# Run the app
mvn spring-boot:run


