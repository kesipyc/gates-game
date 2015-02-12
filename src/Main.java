import java.util.ArrayList;
import java.util.List;

public class Main {
    private static class Bramka
    {
        boolean otwarta;
        final boolean nagroda;
        boolean wybrana;
        public Bramka(boolean nagroda)
        {
            this.nagroda = nagroda;
        }
        public void otworz()
        {
            otwarta = true;
        }
        public void wybierz()
        {
            wybrana = true;
        }

        @Override
        public String toString()
        {
            return "Bramka{" +
                   "otwarta=" + otwarta +
                   ", nagroda=" + nagroda +
                   ", wybrana=" + wybrana +
                   '}';
        }
    }

    private static class Gra
    {
        final List<Bramka> bramki = new ArrayList();

        @Override
        public String toString()
        {
            return "Gra{" +
                   "bramki=" + bramki +
                   '}';
        }

        public Gra()
        {
            int rand = random3();
            switch (rand)
            {
                case 0:
                    bramki.add(new Bramka(true));
                    bramki.add(new Bramka(false));
                    bramki.add(new Bramka(false));
                    break;
                case 1:
                    bramki.add(new Bramka(false));
                    bramki.add(new Bramka(true));
                    bramki.add(new Bramka(false));
                    break;
                default:
                    bramki.add(new Bramka(false));
                    bramki.add(new Bramka(false));
                    bramki.add(new Bramka(true));
                    break;
            }
        }

        public void graczWybieraJedna()
        {
            int rand = random3();
            bramki.get(rand).wybierz();
        }

        public void otworzLosowo()
        {
            int rand = random2();
            for (Bramka bramka : bramki)
            {
                if (!bramka.wybrana)
                {
                    if (rand == 0)
                    {
                        bramka.otworz();
                        break;
                    }
                    else
                    {
                        rand--;
                    }
                }
            }
        }

        public void otworzZWiedza()
        {
            for (Bramka bramka : bramki)
            {
                if (!bramka.nagroda && !bramka.wybrana)
                {
                    bramka.otworz();
                }
            }
        }

        public boolean otwartoPustaBramke()
        {
            for (Bramka bramka : bramki)
            {
                if (bramka.wybrana && bramka.otwarta)
                {
                    throw new IllegalStateException();
                }
                else if (bramka.otwarta && bramka.nagroda)
                {
                    return false;
                }
            }
            return true;
        }

        public boolean oplacaSieZmieniac()
        {
            for (Bramka bramka : bramki)
            {
                if (bramka.wybrana && bramka.nagroda)
                {
                    return false;
                }
            }
            return true;
        }
    }

    private static int random3()
    {
        final double rand = Math.random();
        if (rand < 1.0/3.0)
        {
            return 0;
        }
        else if (rand < 2.0/3.0)
        {
            return 1;
        }
        else return 2;
    }

    private static int random2()
    {
        final double rand = Math.random();
        if (rand < 1.0/2.0)
        {
            return 0;
        }
        else return 1;
    }

    public static void main(String[] args) {

        int ileWszystkichGier = 0;
        int ileRazyOtwartoPustaBramke = 0;
        int ileRazyOplacaloSieZmienic = 0;
        while (ileRazyOtwartoPustaBramke < 10000)
        {
            ileWszystkichGier++;
            Gra gra = new Gra();
            gra.graczWybieraJedna();
            //gra.otworzZWiedza();
            gra.otworzLosowo();
            if (gra.otwartoPustaBramke())
            {
                ileRazyOtwartoPustaBramke++;
                if (gra.oplacaSieZmieniac())
                {
                    ileRazyOplacaloSieZmienic++;
                }
            }
            //System.out.println("Gra numer " + ileWszystkichGier + ": " + gra);
        }
        System.out.println("Rozegrano " + ileWszystkichGier + " gier");
        System.out.println(" z czego " + ileRazyOtwartoPustaBramke + " spelnia warunki (otwarto pusta bramke)");
        System.out.println(" Oplacalo sie zmieniac " + ileRazyOplacaloSieZmienic + " razy czyli p = " + (double)ileRazyOplacaloSieZmienic/ ileRazyOtwartoPustaBramke);
    }
}
