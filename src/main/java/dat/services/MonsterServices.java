package dat.services;

import dat.dtos.MonsterDTO;
import services.FetchTools;

public class MonsterServices {

    private static final String BASE_URL = "https://www.dnd5eapi.co/api/2014/monsters/";

    public static MonsterDTO fetchMonsterByName(String monsterName) {
        // Laver mellemrum til "-"
        String formattedName = monsterName.trim().toLowerCase().replace(" ", "-");

        String uri = BASE_URL + formattedName;

        FetchTools fetchTools = new FetchTools();

        // Brug fetcher
        MonsterDTO monster = fetchTools.getFromApi(uri, MonsterDTO.class);

        if (monster == null) {
            System.out.println("Kunne ikke hente monster: " + monsterName);
        }

        return monster;
    }
}
