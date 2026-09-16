package com.example;

import java.util.ArrayList;

// Cette classe contient les méthodes de sélection des profils
// selon différents critères (distance ou score de motivation).
//
// LocalScore :
// Les candidats sont triés par distance puis sélectionnés
// en alternant les plus proches et les plus éloignés
// afin de maximiser les écarts dans le groupe.
//
// SkillScore :
// Les candidats sont triés par score de motivation.
// Ensuite, on recherche le groupe de taille fixe
// ayant le plus petit écart global entre le score minimum
// et le score maximum.

public class ProfileSelectionMethods
{
	public static ArrayList<ApplicationProfile> selectByLocalScore(ArrayList<ApplicationProfile> profilesList, ArrayList<ApplicationProfile> allSelectedProfilesList)
	{
		ArrayList<ApplicationProfile> selectedProfilesList = new ArrayList<>();

		ArrayList<ApplicationProfile> availableCandidateList = getAvailableCandidates(profilesList, allSelectedProfilesList);

		if (availableCandidateList.size() < Match.MAX_CANDIDATES_NUMBERS)
		{
			return selectedProfilesList;
		}

		// Tri des candidats par distance croissante
		sortByDistance(availableCandidateList);

		// Index du candidat le plus proche
		int leftIndex = 0;

		// Index du candidat le plus éloigné
		int rightIndex = availableCandidateList.size() - 1;

		// Sélection alternée :
		// proche -> éloigné -> proche -> éloigné
		// afin de maximiser les écarts de distance
		while (selectedProfilesList.size() < Match.MAX_CANDIDATES_NUMBERS)
		{
			selectedProfilesList.add(availableCandidateList.get(leftIndex));

			leftIndex++;

			if (selectedProfilesList.size() < Match.MAX_CANDIDATES_NUMBERS)
			{
				selectedProfilesList.add(availableCandidateList.get(rightIndex));

				rightIndex--;
			}
		}

		allSelectedProfilesList.addAll(selectedProfilesList);

		return selectedProfilesList;
	}

	public static ArrayList<ApplicationProfile> selectBySkillScore(ArrayList<ApplicationProfile> profilesList, ArrayList<ApplicationProfile> allSelectedProfilesList)
	{
		ArrayList<ApplicationProfile> selectedProfilesList = new ArrayList<>();

		ArrayList<ApplicationProfile> availableCandidateList = getAvailableCandidates(profilesList, allSelectedProfilesList);

		if (availableCandidateList.size() < Match.MAX_CANDIDATES_NUMBERS)
		{
			return selectedProfilesList;
		}

		// Tri des candidats par score de motivation croissant
		sortBySkillScore(availableCandidateList);

		// Contient le plus petit écart trouvé
		int smallestDifference = Integer.MAX_VALUE;

		// Position de départ du meilleur groupe trouvé
		int bestStartIndex = 0;

		// Recherche du groupe ayant le plus petit écart global
		for (int index = 0; index <= availableCandidateList.size() - Match.MAX_CANDIDATES_NUMBERS; index++)
		{
			ApplicationProfile firstCandidate = availableCandidateList.get(index);

			ApplicationProfile lastCandidate = availableCandidateList.get(index + Match.MAX_CANDIDATES_NUMBERS - 1);

			int currentDifference = lastCandidate.getMotivationScore() - firstCandidate.getMotivationScore();

			// Si l'écart actuel est plus petit,
			// alors ce groupe devient le meilleur groupe
			if (currentDifference < smallestDifference)
			{
				smallestDifference = currentDifference;

				bestStartIndex = index;
			}
		}

		// Ajout des candidats du meilleur groupe trouvé
		for (int index = bestStartIndex; index < bestStartIndex + Match.MAX_CANDIDATES_NUMBERS; index++)
		{
			selectedProfilesList.add(availableCandidateList.get(index));
		}

		allSelectedProfilesList.addAll(selectedProfilesList);

		return selectedProfilesList;
	}

	// Retourne uniquement les candidats
	// qui n'ont pas encore été sélectionnés
	private static ArrayList<ApplicationProfile> getAvailableCandidates(ArrayList<ApplicationProfile> profilesList, ArrayList<ApplicationProfile> allSelectedProfilesList)
	{
		ArrayList<ApplicationProfile> availableCandidateList = new ArrayList<>();

		for (ApplicationProfile candidateProfile : profilesList)
		{
			if (!allSelectedProfilesList.contains(candidateProfile))
			{
				availableCandidateList.add(candidateProfile);
			}
		}

		return availableCandidateList;
	}

	// Tri à bulles des candidats par distance croissante
	private static void sortByDistance(ArrayList<ApplicationProfile> candidateList)
	{
		for (int firstIndex = 0; firstIndex < candidateList.size() - 1; firstIndex++)
		{
			for (int secondIndex = 0; secondIndex < candidateList.size() - firstIndex - 1; secondIndex++)
			{
				ApplicationProfile firstCandidate = candidateList.get(secondIndex);

				ApplicationProfile secondCandidate = candidateList.get(secondIndex + 1);

				if (firstCandidate.getDistance() > secondCandidate.getDistance())
				{
					candidateList.set(secondIndex, secondCandidate);

					candidateList.set(secondIndex + 1, firstCandidate);
				}
			}
		}
	}

	// Tri à bulles des candidats par score de motivation croissant
	private static void sortBySkillScore(ArrayList<ApplicationProfile> candidateList)
	{
		for (int firstIndex = 0; firstIndex < candidateList.size() - 1; firstIndex++)
		{
			for (int secondIndex = 0; secondIndex < candidateList.size() - firstIndex - 1; secondIndex++)
			{
				ApplicationProfile firstCandidate = candidateList.get(secondIndex);

				ApplicationProfile secondCandidate = candidateList.get(secondIndex + 1);

				if (firstCandidate.getMotivationScore() > secondCandidate.getMotivationScore())
				{
					candidateList.set(secondIndex, secondCandidate);

					candidateList.set(secondIndex + 1, firstCandidate);
				}
			}
		}
	}
}