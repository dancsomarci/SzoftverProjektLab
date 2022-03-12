# Szoftver Projekt Labor

- anyagok
  - genetikai kód
    - meghatároz egy ágenst
  - aminosav (alapból fix egység, pl 20)
  - aminosav (alapból fix egység, pl 30)

- ágens
  - vírus
    - "sebez"
  - vakcina
    - "gyógyít"

- interakciók
  - érinthetőség
    - egy mezőn vannak
    - nincs érinthetőséget blokkoló effekt
  - looting
    - csak érinthető játékost lehet lootolni
    - csak akkor lehet lootolni, ha van szabad helyed tárgynak
    - el lehet venni egy tárgyat
    - kiválasztható, de a típusát nem látod
  - felkenés
    - aminosav és nukleotid szükséges
    - olyan mint a "cast spell" interakció
    - a hatás időtartama körök számában van megadva

- tárgyak
  - védőfelszerelés
    - egyszerre max 3 viselhető (linked list érdemes)
    - lehetnek duplikáltak
    - zsák
      - növeli az aminosav és nukleotid kapacitást 20-kal
    - kesztyű
      - a viselőjére felkent ágenst hatását blokkolja
    - védőköpeny
      - a viselőjére felkent ágenst 82.3%-os valószínűséggel blokkolja
  
- pálya
  - mezők
    - tetszőleges számú szomszédja, ami szintén mező
    - sima mező
    - védőhely
    - labor
      - genetikai kód
        - marad a laborban megtanulás után
    - raktár
      - védőfelszerelés
        - eltűnik a raktárból felvevés után
        - csak akkor tudod felvenni (megnézni) ha van szabad helyed

- kör
  - legfeljebb 3 interakció
  - kör befejezése bármikor
