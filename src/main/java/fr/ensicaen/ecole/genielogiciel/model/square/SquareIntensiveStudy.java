package fr.ensicaen.ecole.genielogiciel.model.square;

import fr.ensicaen.ecole.genielogiciel.LoginMain;
import fr.ensicaen.ecole.genielogiciel.model.character.AbstractFactoryStudent;

import java.util.List;

public class SquareIntensiveStudy implements Square{

    public SquareIntensiveStudy() {}

    @Override
    public void execute(AbstractFactoryStudent student, int diceTotal, List<Square> board) {
        student.increaseSkillLevel(3);
        System.out.println("Square N" + student.getSquareNumber() + ": " + board.get(student.getSquareNumber()).getSquareName());
        student.setSquareNumber(12);
        student.addRoundPositions(student.getSquareNumber());
        board.get(student.getSquareNumber()).execute(student, diceTotal, board);
    }

    @Override
    public String getSquareName() {
        return LoginMain.getMessageBundle().getString("squarename.Intensive");
    }
}