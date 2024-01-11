from sqlalchemy import create_engine, Column, Integer, String, ForeignKey, Float, JSON
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, relationship
import pandas as pd
import re
from tqdm import tqdm

DB_USERNAME = 'DELL'
DB_PASSWORD = ''
DB_HOST = 'localhost'
DB_PORT = '3306'
DB_NAME = 'stockify'

DATABASE_URI = f'mysql+mysqlconnector://{DB_USERNAME}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}'

Base = declarative_base()

class CategorieDeRecette(Base):
    __tablename__ = 'categorie_de_recette'

    id = Column(Integer, primary_key=True)
    intitule = Column(String)

class Recette(Base):
    __tablename__ = 'recette'

    id = Column(Integer, primary_key=True)
    intitule = Column(String)
    description = Column(String)
    image_url = Column(String)
    instructions_de_preparation = Column(JSON)  # Changed to JSON for a list of strings
    ingredients = relationship("Ingredient", back_populates="recette")  # Relationship to Ingredient table
    is_deleted = Column(Integer)
    duree_total = Column(Integer)
    recommendation_id = Column(Integer)
    stock_id = Column(Integer)
    categorie_id = Column(Integer, ForeignKey('categorie_de_recette.id'))
    valeur_nutritionnel_id = Column(Integer, ForeignKey('valeur_nutritionnel.id'))  # Reference to ValeurNutritionnel table

class Ingredient(Base):
    __tablename__ = 'ingredient'

    id = Column(Integer, primary_key=True)
    recette_id = Column(Integer, ForeignKey('recette.id'))
    intitule = Column(String)
    quantity = Column(String)
    repas_id = Column(Integer)
    recette = relationship("Recette", back_populates="ingredients")

class ValeurNutritionnel(Base):
    __tablename__ = 'valeur_nutritionnel'

    id = Column(Integer, primary_key=True)
    carbohydrate = Column(Float)
    enegie = Column(Float)
    fibre = Column(Float)
    lipide = Column(Float)
    proteine = Column(Float)
    sucre = Column(Float)

def process_categorie_de_recette(categories, session):
    for category_name in tqdm(categories, total=len(categories), desc="Processing categorie_de_recette"):
        if pd.notna(category_name):
            existing_category = session.query(CategorieDeRecette).filter_by(intitule=category_name).first()

            if not existing_category:
                categorie_instance = CategorieDeRecette(intitule=category_name)
                session.add(categorie_instance)

def process_recipes(row, session):
    not_found_link = 'data:image/png;base64,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'
    categorieId = session.query(CategorieDeRecette.id).filter_by(intitule=row['RecipeCategory']).first()[0] if pd.notna(row['RecipeCategory']) else None

    description = row["Description"]
    if pd.isna(description):  # Check if the value is NaN
        description = None

    # Check if 'Images' is a string and not NaN before applying re.search
    images_value = row.get('Images', '')
    image_url = re.search(r'"(https://[^"]+)"', images_value).group(1) if isinstance(images_value, str) and re.search(r'"(https://[^"]+)"', images_value) else not_found_link

    recette = Recette(
        id=row["RecipeId"],
        intitule=row["Name"],
        description=description,
        image_url=image_url,
        instructions_de_preparation=[s.strip() for s in re.split(r'\. |, |.,', re.sub(r"[c()\"\n]", "", row['RecipeInstructions']))] if 'RecipeInstructions' in row and pd.notna(row['RecipeInstructions']) else [],
        recommendation_id=None,
        stock_id=None,
        categorie_id=categorieId,
        duree_total=parse_total_time(row['TotalTime']),
        is_deleted=0
    )

    valeur_nutritionnel = ValeurNutritionnel(
        carbohydrate=row['CarbohydrateContent'],
        enegie=row['Calories'],
        fibre=row['FiberContent'],
        lipide=row['FatContent'],
        proteine=row['ProteinContent'],
        sucre=row['SugarContent']
    )
    session.add(valeur_nutritionnel)
    session.flush()
    recette.valeur_nutritionnel_id = valeur_nutritionnel.id
    session.add(recette)

def process_ingredients(row, session):
    #Ingrédients
    cleaned_ingredients = re.sub(r"[c()\"\n]", "", row['RecipeIngredientParts']) if 'RecipeIngredientParts' in row and pd.notna(row['RecipeIngredientParts']) else ""
    split_ingredients = [s.strip() for s in re.split(r', ', cleaned_ingredients)]
    result_ingredients = list(map(str.strip, split_ingredients))

    #Quantities
    cleaned_quantities = re.sub(r"[c()\"\n]", "", row['RecipeIngredientQuantities']) if pd.notna(row['RecipeIngredientQuantities']) else ""
    split_quantities = re.split(r', | |-', cleaned_quantities)
    result = list(map(str.strip, split_quantities)) if pd.notna(row['RecipeIngredientQuantities']) else []
    result_quantities = [x if x != "NA" else "0" for x in result]
    result_quantities = [x if x != '1⁄2' else "1/2" for x in result_quantities]
    result_quantities = [x if bool(re.match(r'\d+[\.\/]?\d*', x)) else '0' for x in result_quantities][:len(split_ingredients)]

    for ingredient_str, ingredient_quantity in zip(result_ingredients, result_quantities):
        existing_ingredient = session.query(Ingredient).filter_by(recette_id=row["RecipeId"], intitule=ingredient_str).first()

        if not existing_ingredient:
            ingredient = Ingredient(
                recette_id=row["RecipeId"],
                intitule=ingredient_str,
                quantity=eval(ingredient_quantity),
                repas_id=None
            )
            session.add(ingredient)

def main():
    engine = create_engine(DATABASE_URI)
    Base.metadata.create_all(engine)

    Session = sessionmaker(bind=engine)
    session = Session()

    csv_file = r'./Food-Recipe-Recommandation-System/Data/recipes.csv'
    df = pd.read_csv(csv_file)

    #Categories De Recette
    # categories = df['RecipeCategory'].unique()

    # process_categorie_de_recette(categories, session)

    # session.commit()

    #Recettes
    # for index, row in tqdm(df.iterrows(), total=len(df), desc="Processing recipes"):
    #     process_recipes(row, session)

    # session.commit()

    #Ingrédients
    for index, row in tqdm(df.iterrows(), total=len(df), desc="Processing ingrédients"):
        process_ingredients(row, session)

    session.commit()
    session.close()

def parse_total_time(total_time_str):
    # Regular expression pattern to match hours and minutes in the "PT24H45M" format
    pattern = re.compile(r'PT(?:(\d+)H)?(?:(\d+)M)?')

    match = pattern.match(total_time_str)
    if match:
        hours, minutes = match.groups()
        total_minutes = 0

        if hours:
            total_minutes += int(hours) * 60

        if minutes:
            total_minutes += int(minutes)

        return total_minutes

    return 0

if __name__ == '__main__':
    main()
