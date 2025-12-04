//package sanguine.view;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import sanguine.controller.SanguineStubController;
//import sanguine.model.BasicSanguineModel;
//import sanguine.model.SanguineCard;
//
///**
// * this is a class we ran that makes a view and plays some cards. we used this to test the diplay
// * functionality of the view.
// */
//public class Main {
//
//  /**
//   * this is the main method that we used to run and test our program from. by test, we ran this
//   * program to make sure the view was working as a general object, and then we tested the nuances.
//   *
//   * @param args option parameters from the CLI
//   */
//  public static void main(String [] args)  {
//    try {
//      // 1. Create an instance of the model.
//      BasicSanguineModel model = new BasicSanguineModel();
//      List<SanguineCard> deck1 = model.createDeck();
//      List<SanguineCard> deck2 = model.createDeck();
//
//      for (SanguineCard card : deck2) {
//        deck1.add(card);
//      }
//
//      Map<SanguineCard, Integer> counts = new HashMap<>();
//      Iterator<SanguineCard> iter = deck1.iterator();
//
//      while (iter.hasNext()) {
//        SanguineCard item = iter.next();
//        counts.put(item, counts.getOrDefault(item, 0) + 1);
//
//        if (counts.get(item) > 2) {
//          iter.remove();
//        }
//      }
//
//      deck2.clear();
//      for (SanguineCard card : deck1) {
//        deck2.add(card);
//      }
//
//      model.startGame(5, 7, deck1, deck2, 3);
//      System.out.println(deck1.get(2));
//      model.playTurn(0, 0, deck1.get(2));
//      model.playTurn(2, 6, deck2.get(2));
//      model.playTurn(3, 0, deck1.get(1));
//      model.playTurn(4, 6, deck2.get(5));
//      model.playTurn(4, 0, deck1.get(7));
//
//      SanguineStubController controller = new SanguineStubController(model);
//      SanguineViewFrame frame = new SanguineViewFrame(model, controller);
//
//      controller.setView(frame);
//
//      frame.refresh();
//    } catch (Exception exo) {
//      exo.printStackTrace();
//    }
//  }
//}
