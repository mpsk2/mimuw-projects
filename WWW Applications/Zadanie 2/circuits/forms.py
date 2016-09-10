from django import forms


# we define form do a post
class EditCommissionForm(forms.Form):
    old_ballots_got = forms.IntegerField()
    old_allowed_to_vote = forms.IntegerField()
    ballots_got = forms.IntegerField(label='Otrzymane karty do glosowania', required = True)
    allowed_to_vote = forms.IntegerField(label='Uprawnieni do glosowania', required = True)
    commission_id = forms.IntegerField(min_value = 0)
