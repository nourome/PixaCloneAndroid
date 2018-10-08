package noro.me.pixacloneandroid.model

import noro.me.pixacloneandroid.R


enum class PhotosCategory(val value: Int, val image: Int, val category: String) {
    Fashion(R.string.cat_fashion, R.drawable.fashion, "fashion"),
    Nature(R.string.cat_nature, R.drawable.nature, "nature"),
    Backgrounds(R.string.cat_background, R.drawable.backgrounds, "backgrounds"),
    Science(R.string.cat_science, R.drawable.science, "science"),
    Education(R.string.cat_education, R.drawable.education, "education"),
    People(R.string.cat_people, R.drawable.people, "people"),
    Feelings(R.string.cat_feelings, R.drawable.feelings, "feelings"),
    Religion(R.string.cat_religion, R.drawable.religion, "religion"),
    Health(R.string.cat_health, R.drawable.health, "health"),
    Places(R.string.cat_places, R.drawable.places, "places"),
    Buildings(R.string.cat_building, R.drawable.buildings, "buildings"),
    Animals(R.string.cat_animals, R.drawable.animals, "animals"),
    Industry(R.string.cat_industry, R.drawable.industry, "industry"),
    Food(R.string.cat_food, R.drawable.food,  "food"),
    Computer(R.string.cat_computer, R.drawable.computer, "computer"),
    Sports(R.string.cat_sport, R.drawable.sports, "sports"),
    Transportation(R.string.cat_transportation, R.drawable.transportation, "transportation"),
    Travel(R.string.cat_travel, R.drawable.travel, "travel"),
    Business(R.string.cat_business,R.drawable.business, "business"),
    Music(R.string.cat_music, R.drawable.music, "music")

    //static let list: [PhotosCategory] = [Fashion, Nature, Backgrounds, Science, Education,  People, Feelings, Religion, Health, Places, Buildings, Animals, Industry, Food, Computer, Sports, Transportation, Travel, Business, Music]

}
