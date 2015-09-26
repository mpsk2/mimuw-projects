from django.shortcuts import render, get_object_or_404
from django.http import Http404
from django.template import RequestContext, loader

from django.db import IntegrityError, transaction
import time

from circuits.models import ElectoralCircuit, ElectoralCommission
from circuits.forms import EditCommissionForm

# Create your views here.
def index(request):
    circuits = ElectoralCircuit.objects.filter(super_circuit__isnull = True)

    context = RequestContext(request, {
        'data': circuits,
        'data_name': 'okregi',
    })
    return render(request, 'circuits/circuit.html', context)


def circuits(request, circuit_id):
    circuit = ElectoralCircuit.objects.get(number = circuit_id)
    circuits = ElectoralCircuit.objects.filter(super_circuit__in = [circuit_id])
    commissions = ElectoralCommission.objects.filter(circuit__in = [circuit_id])
    
    context = RequestContext(request, {})
    if circuits:
        context = RequestContext(request, {
            'circuit': circuit,
            'data': circuits,
            'data_name': 'okregi',
        })
    else:
        context = RequestContext(request, {
            'circuit': circuit,
            'data': commissions,
            'data_name': 'komisje',
        })
    return render(request, 'circuits/circuit.html', context)


def commission(request, commission_id):
    commission = get_object_or_404(ElectoralCommission, id = commission_id)

    context = RequestContext(request, {
        'commission': commission,
    })
    return render(request, 'circuits/commission.html', context)

# my exceptions
class NegativeValueException(Exception):
    def __init__(self, which):
        self.which = which

    def __str__(self):
        return "Ziom, " + str(self.which) + " miała być nieujemna."


class OneWasFasterException(Exception):
    def __init__(self, which):
        self.which = which

    def __str__(self):
        return "Sory majster, ale ktoś cię ubiegł i zmienił " + str(self.which) + " w miedzyczasie"

@transaction.atomic
def edit_commission(request, commission_id):
    if not request.method == 'POST': 
        commission = get_object_or_404(ElectoralCommission, id = commission_id)
    
        if not commission.ballots_got:
            commission.ballots_got = 0
        if not commission.allowed_to_vote:
            commission.allowed_to_vote = 0
        form = EditCommissionForm()

        context = RequestContext(request, {
            'commission': commission,
            'form': form,
        })
        return render(request, 'circuits/edit_commission.html', context)
    try:
        form = EditCommissionForm(request.POST)
        form.is_valid()

        with transaction.atomic():
            commission = get_object_or_404(ElectoralCommission, id = form.cleaned_data['commission_id'])
            if not commission.ballots_got == form.cleaned_data['old_ballots_got'] and commission.ballots_got:
                raise OneWasFasterException("liczbę kart do głosowania")
            if not commission.allowed_to_vote == form.cleaned_data['old_allowed_to_vote'] and commission.allowed_to_vote:
                raise OneWasFasterException("liczbę osób uprawnionych do głosowania")
            
            temp = form.cleaned_data['allowed_to_vote']
            if temp < 0:
                raise NegativeValueException("liczba uprawnionych do głosowania")
            commission.allowed_to_vote = temp
            temp = form.cleaned_data['ballots_got']
            if temp < 0:
                raise NegativeValueException("liczba kart do głosowania")
            commission.ballots_got = temp
            commission.save()
        context = RequestContext(request, {
            'commission': commission,
            'form': form,
        })
        return render(request, 'circuits/commission.html', context)
    except Exception as ex:
        commission = get_object_or_404(ElectoralCommission, id = commission_id)

        if not commission.ballots_got:
            commission.ballots_got = 0
        if not commission.allowed_to_vote:
            commission.allowed_to_vote = 0
        form = EditCommissionForm()

        context = RequestContext(request, {
            'commission': commission,
            'form': form,
            'error': str(ex),
        })

        return render(request, 'circuits/edit_commission.html', context)
    return render(request, 'circuits/error.html', RequestContext(request, {}))

