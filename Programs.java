
public class Programs {

        public static Program fibonacci10 = new Program("Fibonacci10", new Word[] { 
                        new Word(Opcode.LDI, 1, -1, 0), new Word(Opcode.STD, 1, -1, 20),
                        new Word(Opcode.LDI, 2, -1, 1), new Word(Opcode.STD, 2, -1, 21),
                        new Word(Opcode.LDI, 0, -1, 22),
                        new Word(Opcode.LDI, 6, -1, 6), new Word(Opcode.LDI, 7, -1, 30), new Word(Opcode.LDI, 3, -1, 0),
                        new Word(Opcode.ADD, 3, 1, -1), new Word(Opcode.LDI, 1, -1, 0), new Word(Opcode.ADD, 1, 2, -1),
                        new Word(Opcode.ADD, 2, 3, -1), new Word(Opcode.STX, 0, 2, -1), new Word(Opcode.ADDI, 0, -1, 1),
                        new Word(Opcode.SUB, 7, 0, -1), new Word(Opcode.JMPIG, 6, 7, -1),
                        new Word(Opcode.STOP, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1)
        });

        public static Program fatorial = new Program("Fatorial", new Word[] {
                        new Word(Opcode.LDI, 0, -1, 6), 
                        new Word(Opcode.LDI, 1, -1, 1), 
                        new Word(Opcode.LDI, 6, -1, 1), 
                        new Word(Opcode.LDI, 7, -1, 8), 
                        new Word(Opcode.JMPIE, 7, 0, 0),
                        new Word(Opcode.MULT, 1, 0, -1),
                        new Word(Opcode.SUB, 0, 6, -1), 
                        new Word(Opcode.JMP, -1, -1, 4),
                        new Word(Opcode.STD, 1, -1, 10),
                        new Word(Opcode.STOP, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1) } 
        );

        public static Program pa = new Program("PA", new Word[] { new Word(Opcode.LDI, 2, -1, 1),
                        new Word(Opcode.LDI, 3, -1, 1),
                        new Word(Opcode.LDI, 4, -1, 21),
                        new Word(Opcode.LDD, 1, -1, 20),
                        new Word(Opcode.SUBI, 1, -1, 2),
                        new Word(Opcode.LDD, 5, -1, 20),
                        new Word(Opcode.LDI, 7, -1, 19),
                        new Word(Opcode.LDI, 6, -1, 17), 
                        new Word(Opcode.JMPIL, 6, 5, -1),

                        new Word(Opcode.JMPIL, 7, 1, -1), 
                        new Word(Opcode.STX, 4, 2, -1), 
                        new Word(Opcode.ADDI, 4, -1, 1), 
                        new Word(Opcode.STX, 4, 3, -1),
                        new Word(Opcode.ADD, 3, 2, -1),
                        new Word(Opcode.LDX, 2, 4, -1), 
                        new Word(Opcode.SUBI, 1, -1, 1), 
                        new Word(Opcode.JMP, -1, -1, 9), 

                        new Word(Opcode.LDI, 1, -1, -1),
                        new Word(Opcode.STD, 1, -1, 20), 
                        new Word(Opcode.STOP, -1, -1, -1),

                        new Word(Opcode.DATA, -1, -1, 10), 

                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1) });


        public static Program pb = new Program("PB", new Word[] { new Word(Opcode.LDI, 2, -1, 1),
                        new Word(Opcode.LDI, 3, -1, 13),
                        new Word(Opcode.LDD, 1, -1, 16), 
                        new Word(Opcode.LDD, 5, -1, 16), 
                        new Word(Opcode.LDI, 7, -1, 9), 
                        new Word(Opcode.LDI, 6, -1, 10), 
                        new Word(Opcode.JMPIL, 6, 5, -1),

                        new Word(Opcode.JMPIG, 3, 1, -1),
                        new Word(Opcode.STD, 2, -1, 17), 
                        new Word(Opcode.STOP, -1, -1, -1),

                        new Word(Opcode.LDI, 1, -1, -1),
                        new Word(Opcode.STD, 1, -1, 17),
                        new Word(Opcode.STOP, -1, -1, -1), 

                        new Word(Opcode.MULT, 2, 1, -1),
                        new Word(Opcode.SUBI, 1, -1, 1), 
                        new Word(Opcode.JMP, -1, -1, 7), 

                        new Word(Opcode.DATA, -1, -1, 7), 

                        new Word(Opcode.DATA, -1, -1, -1) 
        });


        public static Program pc = new Program("PC", new Word[] {

                        new Word(Opcode.LDI, 1, -1, 17),
                        new Word(Opcode.STD, 1, -1, 48),
                        new Word(Opcode.LDI, 1, -1, 21), new Word(Opcode.STD, 1, -1, 49),
                        new Word(Opcode.LDI, 1, -1, 4), new Word(Opcode.STD, 1, -1, 50),
                        new Word(Opcode.LDI, 1, -1, 3), new Word(Opcode.STD, 1, -1, 51),
                        new Word(Opcode.LDI, 1, -1, 7), new Word(Opcode.STD, 1, -1, 52),

                        new Word(Opcode.LDI, 1, -1, 48),
                        new Word(Opcode.STD, 1, -1, 43),
                        new Word(Opcode.STD, 1, -1, 44),
                        new Word(Opcode.STD, 1, -1, 45),
                        new Word(Opcode.LDI, 1, -1, 49),
                        new Word(Opcode.STD, 1, -1, 46),
                        new Word(Opcode.LDI, 1, -1, 53),
                        new Word(Opcode.STD, 1, -1, 47),
                        new Word(Opcode.LDD, 1, -1, 44),
                        new Word(Opcode.LDD, 2, -1, 45),
                        new Word(Opcode.LDD, 3, -1, 46),
                        new Word(Opcode.LDX, 4, 2, -1),
                        new Word(Opcode.LDX, 5, 3, -1),
                        new Word(Opcode.SUB, 4, 5, -1),
                        new Word(Opcode.LDI, 5, -1, 31),
                        new Word(Opcode.JMPIL, 5, 4, -1),
                        new Word(Opcode.LDX, 4, 2, -1),
                        new Word(Opcode.LDX, 5, 3, -1),
                        new Word(Opcode.SWAP, 4, 5, -1),
                        new Word(Opcode.STX, 2, 4, -1), 
                        new Word(Opcode.STX, 3, 5, -1), 
                        new Word(Opcode.ADDI, 2, -1, 1),
                        new Word(Opcode.ADDI, 3, -1, 1),
                        new Word(Opcode.LDD, 4, -1, 47), 
                        new Word(Opcode.SUB, 4, 3, -1),
                        new Word(Opcode.LDI, 5, -1, 21),
                        new Word(Opcode.JMPIG, 5, 4, -1),
                        new Word(Opcode.ADDI, 1, -1, 1),
                        new Word(Opcode.LDD, 4, -1, 47),
                        new Word(Opcode.SUB, 4, 1, -1),
                        new Word(Opcode.LDI, 5, -1, 19),
                        new Word(Opcode.JMPIG, 5, 4, -1),
                        new Word(Opcode.STOP, -1, -1, -1),

                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1), new Word(Opcode.DATA, -1, -1, -1),
                        new Word(Opcode.DATA, -1, -1, -1) });

        public static Program testIn = new Program("Test In",
                        new Word[] { new Word(Opcode.LDI, 7, -1, 1), new Word(Opcode.LDI, 8, -1, 4),
                                        new Word(Opcode.TRAP, -1, -1, -1), new Word(Opcode.STOP, -1, -1, -1),

                                        new Word(Opcode.DATA, -1, -1, -1)
                        });

        public static Program testOut = new Program("Test Out",
                        new Word[] { new Word(Opcode.LDI, 7, -1, 2), new Word(Opcode.LDI, 8, -1, 6),
                                        new Word(Opcode.LDI, 1, -1, 800), new Word(Opcode.STD, 1, -1, 6),
                                        new Word(Opcode.TRAP, -1, -1, -1),
                                        new Word(Opcode.STOP, -1, -1, -1),

                                        new Word(Opcode.DATA, -1, -1, -1) 
                        });
}