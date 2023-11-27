public class Word {
    public Opcode opc;
    public int r1;
    public int r2; 
    public int p;

    public Word(Opcode _opc, int _r1, int _r2, int _p) {
        opc = _opc;
        r1 = _r1;
        r2 = _r2;
        p = _p;
    }
}