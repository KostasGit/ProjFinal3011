import java.util.*;

public class Menu {
    private String licensePlate; //arxikh pinakida tou xrhsth

    public boolean validLicensePlate(String licensePlate) {
        if (licensePlate.matches("[a-zA-Z][a-zA-Z][a-zA-Z]-[0-9][0-9][0-9][0-9]")) {
            //gia mono kefalaia : [A-Z]
            this.licensePlate = licensePlate;
            return true;
        } else {
            return false;
        }
    }

    //TODO F1
    public void vehicleInsuranceStatus(List<Vehicles> vehiclesInf, String licensePlate, List<Vehicles> vehiclesInfDB, int readFrom, int writeTo) throws Exception {

        ExportFile fileExport = new ExportFile();
        if (writeTo == 2) {
            fileExport.writeToCsv("vehicleInsuranceStatus.csv", "No match found");
            System.out.println("CsvParser file generated");
        } //if selected write to csv->create empty file with no match entry.If we find something overwrite this value...

        Dates d = new Dates();
        //*********************FOR CsvParser*******************\\
        if (readFrom == 1) {//csv
            for (Vehicles V : vehiclesInf) {

                if(d.Date2afterDate1(V.getFinishDayInsu())){
                    System.out.println("PETYXEEEEEEE");
                }

                if (V.getPlateNumber().equals(licensePlate)) {    // test gia sigkrisi ktl
                    if (d.Date2afterDate1(V.getFinishDayInsu())) {
                        readFrom++;
                        if (writeTo == 1) {
                            //    System.out.println("wpapapapappapapapaaaaaaaaaaaaaa");
                            System.out.println("Your insurance ends at: " + V.getFinishDayInsu());
                        } else {
                            fileExport.writeToCsv("vehicleInsuranceStatus.csv", ("Your insurance ends at: " + V.getFinishDayInsu()));
                        }
                    }
                }

            }
            if (readFrom == 1) {
                System.out.println("No match found");
            }
        } else if (readFrom == 2) {//db

            // *********************FOR DB*******************\\
            for (Vehicles V : vehiclesInfDB) {

                String finishDate = V.getFinishDayInsu();  //x Η ΗΜΕΡΟΜΗΝΙΑ Π ΤΕΛΕΙΩΝΕΙ Η ΑΣΦΑΛΕΙΑ
                if (V.getPlateNumber().equals(licensePlate)) {    // test gia sigkrisi ktl

                    if (d.Date2afterDate1(V.getFinishDayInsu())) {
                        readFrom++;
                        if (writeTo == 1) {
                            System.out.println("Your insurance ends at: " + V.getFinishDayInsu());
                        } else {
                            fileExport.writeToCsv("vehicleInsuranceStatus.csv", "Your insurance ends at: " + V.getFinishDayInsu());
                        }
                    }
                }
            }
            if (readFrom == 2) {
                System.out.println("No match found");
            }
        }
    }

    //TODO F2 user input to days q variable dld
    public void forecomingExpiries(List<Vehicles> vehiclesInf, List<Vehicles> vehiclesInfDB, int readFrom, int writeTo, int days) throws Exception {
        ExportFile fileExport = new ExportFile();
        if (writeTo == 2) {
            fileExport.writeToCsv("forecomingExpiries.csv", "No match found");
            System.out.println("CsvParser file generated");
        } //if selected write to csv->create empty file with no match entry.If we find something overwrite this value...
        Dates d = new Dates();

        //************************CsvParser*********************//
        if (readFrom == 1) {

            for (Vehicles V : vehiclesInf) {

                if (!d.Date2afterDate1(V.getFinishDayInsu())) {  //checks if finishInsuranceDate is before current Date
                } else if (d.Date2afterDate1(V.getFinishDayInsu())) {

                    System.out.println("days between second version" + d.daysBetween(V.getFinishDayInsu()));

                    if (d.daysBetween(V.getFinishDayInsu()) < days) {
                        if (writeTo == 1) {
                            System.out.println("The car's insurance with registration plate number: " + V.getPlateNumber() + " is about to expire.");
                        } else if (writeTo == 2) {
                            if (readFrom == 1) {
                                fileExport.writeToCsv("forecomingExpiries.csv", "The car's insurance with registration plate number: " + V.getPlateNumber() + " is about to expire.");
                                readFrom++;
                            }
                            fileExport.appendToCsv("forecomingExpiries.csv", "," + V.getPlateNumber());
                        }
                    }
                }
            }
        }
        //************************DB*********************//
        else if (readFrom == 2) {
            for (Vehicles V : vehiclesInfDB) {

                if (!d.Date2afterDate1(V.getFinishDayInsu())) {  //checks if finishInsuranceDate is before todays Date
                } else if (d.Date2afterDate1(V.getFinishDayInsu())) {
                    if (d.daysBetween(V.getFinishDayInsu()) < days) {
                        if (writeTo == 1) {
                            System.out.println("The car's insurance with registration plate number: " + V.getPlateNumber() + " is about to expire.");
                        } else if (writeTo == 2) {
                            if (readFrom == 2) {
                                fileExport.writeToCsv("forecomingExpiries.csv", "PlateNumber" + V.getPlateNumber());
                                readFrom++;
                            }
                            fileExport.appendToCsv("forecomingExpiries.csv", "," + V.getPlateNumber());
                        }
                    }
                }
            }
        }
    }

    //TODO F3 SORT F3: Sorting uninsured vehicles based on plates-number
    //The application, if requested, can provide the uninsured vehicles based
    //on the plate numbers, ordered in alphanumerical natural order.
    //TIP: try to implement the sorting solution inside the Java application,
    //and avoid a  DB-prepared ordered result.
    public void platesOrder(List<Vehicles> vehiclesInf, List<Vehicles> vehiclesInfDB, int readFrom, int writeTo) throws Exception {


        Dates d = new Dates();
        ExportFile fileExport = new ExportFile();
        if (writeTo == 2) {
            fileExport.writeToCsv("SortedPlateNumbers.csv", "PlateNumber");
        }

        //************************CsvParser*********************//
        if (readFrom == 1) {

            Collections.sort(vehiclesInf, new Comparator<Vehicles>() {
                @Override
                public int compare(Vehicles o1, Vehicles o2) {
                    return o1.getPlateNumber().compareTo(o2.getPlateNumber());
                }
            });
            System.out.print("The car's plates registration sorting order selected is:\n\t\t\t  [Alpha-Numerical]");
            for (Vehicles V : vehiclesInf) {

                if (!d.Date2afterDate1(V.getFinishDayInsu())) {
                    if (writeTo == 1) {
                        System.out.print("\n\t\t\t\t " + "|" + V.getPlateNumber() + "|");
                    } else if (writeTo == 2) {
                        fileExport.appendToCsv("SortedPlateNumbers.csv", "," + V.getPlateNumber());
                    }
                }
            }
        }
        //************************DB*********************//
        else if (readFrom == 2) {
            Collections.sort(vehiclesInfDB, new Comparator<Vehicles>() {
                @Override
                public int compare(Vehicles o1, Vehicles o2) {
                    return o1.getPlateNumber().compareTo(o2.getPlateNumber());
                }
            });
            System.out.print("The car's plates registration sorting order selected is:\n\t\t\t  [Alpha-Numerical]");

            for (Vehicles V : vehiclesInfDB) {

                if (!d.Date2afterDate1(V.getFinishDayInsu())) {
                    if (writeTo == 1) {
                        System.out.print("\n\t\t\t\t " + "|" + V.getPlateNumber() + "|");
                    } else if (writeTo == 2) {
                        fileExport.appendToCsv("SortedPlateNumbers.csv", "," + V.getPlateNumber());
                    }
                }
            }
        }
    }

    //TODO F4
    public void fineCalcByOwner(List<Vehicles> vehiclesInf, List<Vehicles> vehiclesInfDB, int readFrom, int writeTo, double fine) throws Exception {

        ExportFile fileExport = new ExportFile();
        Dates d = new Dates();

        if (writeTo == 2) {
            fileExport.writeToCsv("fineCalcByOwner.csv", "No match found");
            System.out.println("Csv will be generated");
        }

        Map<String, Integer> fines = new HashMap<>();
        int i = 1;
        //************************CsvParser*********************//
        if (readFrom == 1) {
            for (Vehicles V : vehiclesInf) {

                if(!d.Date2afterDate1(V.getFinishDayInsu()) ){

                    if(fines.get(V.getOwnerName())!=null){

                    //f (V.getOwnerName().equals(fines.get(V.getOwnerName()))) {
                        System.out.println("i am in");
                        // fines.put(V.getOwnerName(), i++);
                        // fines.put(fines, fines.get(Integer) + 1);
                        fines.computeIfPresent(V.getOwnerName(), (k, v) -> v + 1);
                    } else {
                        fines.put(V.getOwnerName(), i);
                    }
                }
            }
        }
        //************************DB*********************//
        else if (readFrom == 2) {

            for (Vehicles V : vehiclesInfDB) {

                // test gia sigkrisi ktl
                if (!d.Date2afterDate1(V.getFinishDayInsu())) {

                    if (fines.containsKey(V.getOwnerName())) {

                            // fines.put(V.getOwnerName(), i++);
                            // fines.put(fines, fines.get(Integer) + 1);
                            fines.computeIfPresent(V.getOwnerName(), (k, v) -> v + 1);
                        } else {
                            fines.put(V.getOwnerName(), i);
                        }
                    }
                }
            }

            int c = 0; //counterfor write=2 and fine=0 case
            for (Map.Entry<String, Integer> entry : fines.entrySet()) {
                String key = entry.getKey();
                Integer tab = entry.getValue();

                if (writeTo == 1) { //writes to cosole
                    if (fine != 0) {
                        System.out.println("The owner " + key + " is obligated to pay a fine of: " + tab * fine + " €.");
                    } else {
                        System.out.println("No user has to pay a fine!");
                        writeTo = 0;
                    }
                } else if (writeTo == 2) {   //writes to csv
                    if (fine != 0) {
                        if (c == 0) {
                            fileExport.writeToCsv("fineCalcByOwner.csv", "CarOwner,FineToPay\n");
                            c++;
                        }
                        fileExport.appendToCsv("fineCalcByOwner.csv", key + "," + tab * fine + "\n");
                    }
                }
            }
        }
    }
