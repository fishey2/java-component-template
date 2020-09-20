# Component Design

Simple application that reads in and stores events for existing patients and then
enriches and projects the status to the message queue.

Endpoints:
* POST /patient - To create patient
* GET /patient/{patientNumber} - To get patient details
* GET /patient/{patientNumber}/status - To get current status
* GET /patient/{patientNumber}/events - To get list of events

Tables:
* Patient
* PatientEvents

Queues:
* Patient.Events (In)
* Patient.Status (Out)
