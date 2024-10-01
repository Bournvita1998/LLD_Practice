# Interview
#
# 90mins
#
# Start: 2:05
# End: 3:35
#
#
# Problem
# Design and implement an in-memory rating service. Using this rating service service admin should be able to create a survey. Inside this survey, admin should be able to add questions, update delete them as well. Questions can have multiple options, options have weights. The average of answers’ weight is the rating of the survey, and average of all ratings is the overall survey rating.
#
# A survey can be shared with registered users.  
#
# Admin:
# 1. Create a survey
# 2. Create questions and options for surveys 
# 3. Define the weightage for each answer 
# 4. Survey response should be rated, overall rating should be calculated, and shown to admin
# 5. Admin should be able to see the average rating of each question
#
# User:
# 1. Users should be able to respond to the provided survey, 
# 2. Users should not be allowed to re-submit a survey
#
#
#
# Approach
# Complied solution
# Correctness
# Own IDE






# Survey
# Id, title, questions, responses, total_rating, shared_with_users_list
# Add_question, remove_question, update_question, add_respose, calculate total rating

# Questions:
# Id, text, options, average_rating
# Add_options, remove_options, update_options, delete_options, calculate_average_rating
# Option:
# Id, text, weight

# User:
# Id, name, response
# respond_to_survey(survey, response), has_responded(survey_id)

# Response:
# User_id, survey_id, question_responses, rating
# calculate_rating(survey)

# Admin
# Id, name
# create_survey(title), view_survey_ratins(survey)




class Option:
    def __init__(self, id, text, weight):
        self.id = id
        self.text = text
        self.weight = weight


class Question:
    def __init__(self, id, text):
        self.id = id
        self.text = text
        self.options = []
        self.average_rating = 0.0

    def add_option(self, option):
        self.options.append(option)

    def remove_option(self, option_id):
        self.options = [option for option in self.options if option.id != option_id]

    def update_options(self, option_id, new_option):
        for i, option in enumerate(self.options):
            if option.id == option_id:
                self.options[i] = new_option
                self.recalculate_ratings()

    def recalculate_ratings(self):
        for survey in all_surveys:
            for response in survey.responses:
                if self.id in response.question_responses:
                    selected_option = response.question_responses[self.id]
                    for option in self.options:
                        if selected_option.id == option.id:
                            response.question_responses[self.id] = option
                    response.rating = response.calculate_rating()
            survey.calculate_total_rating()

    def calculate_average_rating(self, responses):
        total_weight = 0
        count = 0
        for response in responses:
            if self.id in response.question_responses:
                selected_option = response.question_responses[self.id]
                total_weight += selected_option.weight
                count += 1
        self.average_rating = total_weight / count if count else 0


class Survey:
    def __init__(self, id, title):
        self.id = id
        self.title = title
        self.questions = []
        self.responses = []
        self.total_rating = 0.0
        self.shared_with_users = []

    def add_question(self, question):
        self.questions.append(question)

    def remove_question(self, question_id):
        self.questions = [question for question in self.questions if question.id != question_id]

    def update_question(self, question_id, new_question):
        for i, question in enumerate(self.questions):
            if question.id == question_id:
                self.questions[i] = new_question

    def add_response(self, response):
        if not response.user_id in [resp.user_id for resp in self.responses]:
            self.responses.append(response)
            self.calculate_total_rating()

    def calculate_total_rating(self):
        total_rating = 0
        for response in self.responses:
            total_rating += response.rating
        self.total_rating = total_rating / len(self.responses) if self.responses else 0


class Response:
    def __init__(self, user_id, survey_id, question_responses):
        self.user_id = user_id
        self.survey_id = survey_id
        self.question_responses = question_responses
        self.rating = self.calculate_rating()

    def calculate_rating(self):
        total_weight = 0
        count = 0
        for option in self.question_responses.values():
            total_weight += option.weight
            count += 1
        return total_weight / count if count else 0


class User:
    def __init__(self, id, name):
        self.id = id
        self.name = name
        self.responses = []

    def respond_to_survey(self, survey, response):
        if not self.has_responded(survey.id):
            survey.add_response(response)
            self.responses.append(response)
        else:
            print("User already responded")


    def has_responded(self, survey_id):
        return any(resp.survey_id == survey_id for resp in self.responses)


class Admin:
    def __init__(self, id, name):
        self.id = id
        self.name = name

    def create_survey(self, title):
        survey = Survey(len(all_surveys)+1, title)
        all_surveys.append(survey)
        return survey

    def view_survey_ratings(self, survey):
        # survey_rating = calculate_latest_ratings(survey)
        print(f"Survey: {survey.title}, Overall Rating: {survey.total_rating}")
        for question in survey.questions:
            question.calculate_average_rating(survey.responses)
            print(f"Question: {question.text}, Average Rating: {question.average_rating}")

    # def calculate_latest_ratings(self, survey):



all_surveys = []

admin = Admin(1, "Admin")
survey = admin.create_survey("Customer Satisfaction")

question1 = Question(1, "How satisfied are you with the service?")
question1.add_option(Option(1, "Very satisfied", 5))
question1.add_option(Option(2, "Satisfied", 4))
question1.add_option(Option(3, "Average", 3))
question1.add_option(Option(4, "Dissatisfied", 2))
question1.add_option(Option(5, "Very dissatisfied", 1))

question2 = Question(2, "How likely you would recommend it?")
question2.add_option(Option(1, "Very likely", 5))
question2.add_option(Option(2, "Likely", 4))
question2.add_option(Option(3, "Average", 3))
question2.add_option(Option(4, "Unlikely", 2))
question2.add_option(Option(5, "Very unlikely", 1))


survey.add_question(question1)
survey.add_question(question2)

# user1 = User(1, "User1")
# response1 = Response(user1.id, survey.id, {1: question1.options[0], 2: question2.options[1]})
# user1.respond_to_survey(survey, response1)
# user1.respond_to_survey(survey, response1)

user2 = User(2, "User2")
response2 = Response(user2.id, survey.id, {1: question1.options[2], 2: question2.options[3]})
user2.respond_to_survey(survey, response2)


# question1.update_options(1, Option(1, "Very satisfied", 10))
# question1.update_options(1, Option(1, "Very satisfied", -1))



question3 = Question(3, "How likely you would recommend the location?")
question3.add_option(Option(1, "Very likely", 5))
question3.add_option(Option(2, "Likely", 4))
question3.add_option(Option(3, "Average", 3))
question3.add_option(Option(4, "Unlikely", 2))
question3.add_option(Option(5, "Very unlikely", 1))

survey.add_question(question3)


user1 = User(1, "User1")
response1 = Response(user1.id, survey.id, {1: question1.options[0], 2: question2.options[1], 3: question3.options[0]})
user1.respond_to_survey(survey, response1)




admin.view_survey_ratings(survey)

















