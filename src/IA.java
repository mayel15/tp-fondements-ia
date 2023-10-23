public class IA {
    private int[][] grilleEvaluation;
    public IA {
        this.initializeEvaluationGrid();
    }

    public void initializeEvaluationGrid() {
        this.grilleEvaluation = new int[][] {
                {500, -150, 30, 10, 10, 30, -150, 500},
                {-150, -250, 0, 0, 0, 0, -250, -150},
                {30, 0, 1, 2, 2, 1, 0, 30},
                {10, 0, 2, 16, 16, 2, 0, 10},
                {10, 0, 2, 16, 16, 2, 0, 10},
                {30, 0, 1, 2, 2, 1, 0, 30},
                {-150, -250, 0, 0, 0, 0, -250, -150},
                {500, -150, 30, 10, 10, 30, -150, 500}
        };
    }
    public int minmax(Plateau statePlateau, int depth, Joueur joueur,Pion.COULEUR MAX, Pion.COULEUR MIN) {
        if (depth == 0 || isTerminalNode(statePlateau)) {
            return evaluate(statePlateau);
        }
        if(MAX)
        return 0;
    }

    // permet d'evaluer un coup
    public int evaluate(Plateau statePlateau, Pion.COULEUR couleur){
        int score = 0;
        for (int i=0; i<statePlateau.grille.size(); i++){
            for (int j=0; j<statePlateau.grille.size(); j++) {
                // max pour noir &  min pour blanc
                if (statePlateau.grille.get(i).get(j).couleur == couleur) {
                    score = score + grilleEvaluation[i][j];
                } else {
                    score = score - grilleEvaluation[i][j];
                }
            }
        }
        return score;
    }

    // retourne l'ensemble des etats possibles à partir d'un etat donné
    public  Plateau[] generateStatePlateauPossibles(){
        return new Plateau[] {new Plateau(6), new Plateau(6), new Plateau(6)};
    }

    public Plateau meilleurCoup(Pion.COULEUR couleur){
        Plateau meilleurPlateau = null;
        int maxEval = 1000;
        int eval = 0;
        int depth = 6;
        for (Plateau plateau : generateStatePlateauPossibles()) {
            eval = minmax(plateau, depth-1 , couleur);
            if(eval>maxEval) {
                maxEval = eval;
                meilleurPlateau = plateau;
            }
        }
        return meilleurPlateau;
    }

    // permet de verifier s'il y'a un noeud terminal
    public boolean isTerminalNode(Plateau statePlateau) {
        // c'est terminal si la grille est pleine ou personne ne peut plus jouer
        for (int i=0; i<statePlateau.grille.size(); i++){
            for (int j=0; j<statePlateau.grille.size(); j++) {
                // max pour noir &  min pour blanc
                if (statePlateau.grille.get(i).get(j).couleur == Pion.COULEUR.neutre) {
                    return false;
                }

            }
        }
        return true;
    }


/*
    Fonction minimax(état, profondeur, joueur) :
    Si profondeur == 0 ou estTerminalNode(état) :
    Retourner evaluate(état)

    Si joueur == MAX :
    maxEval = -∞
    Pour chaque coup dans generatePossibleStates(état, MAX) :
    eval = minimax(coup, profondeur - 1, MIN)
    maxEval = max(maxEval, eval)
    Retourner maxEval

    Sinon (joueur == MIN) :
    minEval = +∞
    Pour chaque coup dans generatePossibleStates(état, MIN) :
    eval = minimax(coup, profondeur - 1, MAX)
    minEval = min(minEval, eval)
    Retourner minEval

    Fonction evaluate(état) :
    score = 0
    Pour chaque case du plateau dans l'état :
    Si la case est occupée par MAX :
    score = score + valeurDeLaGrille[case]
    Si la case est occupée par MIN :
    score = score - valeurDeLaGrille[case]
    Retourner score

    Fonction meilleurCoup(état) :
    meilleurCoup = null
    maxEval = -∞
    Pour chaque coup dans generatePossibleStates(état, MAX) :
    eval = minimax(coup, profondeur - 1, MIN)
    Si eval > maxEval :
    maxEval = eval
            meilleurCoup = coup
    Retourner meilleurCoup

    Fonction estTerminalNode(état) :
    Si le plateau est rempli (plus aucune case vide) :
        Retourner Vrai

    Si aucun joueur ne peut jouer :
        Retourner Vrai

    Retourner Faux
*/
    /*

    int fonction minimax (int depth)
{
   if (game over or depth = 0)
      return winning score or eval();

   int bestScore;
   move bestMove;

   if (nœud == MAX) { //=Programme
      bestScore = -INFINITY;
      for (each possible move m) {
         make move m;
         int score = minimax (depth - 1)
         unmake move m;
         if (score > bestScore) {
            bestScore = score;
            bestMove = m ;
         }
      }
   }
   else { //type MIN = adversaire
      bestScore = +INFINITY;
      for (each possible move m) {
         make move m;
         int score = minimax (depth - 1)
         unmake move m;
         if (score < bestScore) {
            bestScore = score;
            bestMove = m ;
         }
      }
   }
   return bestscore ;
}

     */
}
