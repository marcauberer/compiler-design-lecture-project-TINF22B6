package com.auberer.compilerdesignlectureproject.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderTest {

    Reader reader;

    @Test
    void readFile(){
        reader = new Reader("testReader.txt");

        reader.expect('H');
        assert reader.getCodeLoc().getColumn() == 0 && reader.getCodeLoc().getLine() == 1;

        reader.expect('e');
        assert reader.getCodeLoc().getColumn() == 1 && reader.getCodeLoc().getLine() == 1;

        reader.expect('l');
        assert reader.getCodeLoc().getColumn() == 2 && reader.getCodeLoc().getLine() == 1;

        reader.expect('l');
        assert reader.getCodeLoc().getColumn() == 3 && reader.getCodeLoc().getLine() == 1;

        reader.expect('o');
        assert reader.getCodeLoc().getColumn() == 4 && reader.getCodeLoc().getLine() == 1;

        reader.expect('!');
        assert reader.getCodeLoc().getColumn() == 1 && reader.getCodeLoc().getLine() == 2;

        assert reader.isEOF();
    }

    @Test
    void readFileError(){
        reader = new Reader("testReader.txt");

        reader.expect('H');
        assert reader.getCodeLoc().getColumn() == 0 && reader.getCodeLoc().getLine() == 1;

        reader.expect('e');
        assert reader.getCodeLoc().getColumn() == 1 && reader.getCodeLoc().getLine() == 1;

        reader.expect('l');
        assert reader.getCodeLoc().getColumn() == 2 && reader.getCodeLoc().getLine() == 1;

        reader.expect('l');
        assert reader.getCodeLoc().getColumn() == 3 && reader.getCodeLoc().getLine() == 1;

        reader.expect('o');
        assert reader.getCodeLoc().getColumn() == 4 && reader.getCodeLoc().getLine() == 1;

        try{
            reader.expect('r');
        }catch (Exception e){
            assert e.getMessage().equals("Expected ! but found r");
        }

    }

}
