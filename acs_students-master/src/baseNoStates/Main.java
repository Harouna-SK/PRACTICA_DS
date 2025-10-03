package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

import baseNoStates.requests.Request;
import baseNoStates.requests.RequestArea;
import baseNoStates.requests.RequestReader;

import java.time.LocalDateTime;

public class Main {
  public static void main(String[] args) {
    /*DirectoryDoors.makeDoors();
    DirectoryUsers.makeUsers();
    new WebServer();*/

    Door d100 = new Door("d100");
    RequestReader request = new RequestReader("95783", "UNLOCK", LocalDateTime.now(), "d100");

    System.out.println(d100);

    d100.processRequest(request);

    System.out.println(d100);
    System.out.println(request);
  }
}
