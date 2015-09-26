__author__ = 'mstankiewicz'

from django.conf.urls import patterns, url

from circuits import views

urlpatterns = patterns(
    '',
    url(r'^$', views.index, name='circuit_index'),
    url(r'^circuits/(?P<circuit_id>\d+)/$', views.circuits, name='circuits'),
    url(r'^ajax_get_hello/(?P<commission_id>\d+)/$', views.ajax_get_commission, name='ajax_get_hello'),
    url(r'^ajax_post_commission/(?P<commission_id>\d+)/$', views.ajax_post_commission, name='ajax_post_commission'),
    )
