'use strict';
function setupClicks(){
    $('.vote').click(handleVote);
}

function handleVote(event){
    event.preventDefault();

    let url = "/vote/";

    $.ajax({
    url: "/vote/",
    method: 'POST',
    data: {answer: event.target.id}
    }).then(results => showResults(results));
}
// ChartJS setup methodology courtesy of
// https://travishorn.com/stacked-bar-chart-with-chart-js-846ebdf11c4e
function showResults(results){
results=JSON.parse(results);
    $('form').css('display','none');
    $('canvas').css('display','block');

    let voteOptionNames = [$('#1').text(), $('#2').text(), $('#3').text(), $('#4').text()];

    if(results.correctAnswer != 0){
        voteOptionNames[results.correctAnswer - 1] = voteOptionNames[results.correctAnswer - 1] + ' (correct answer)';
    }

    var ctx = $('canvas')[0].getContext('2d');

    let data = {
//        labels: voteOptionNames,
        datasets: [{
          label: '"' + voteOptionNames[0] + '": ' + results.answerOneVotes,
          data: [results['answerOneVotes']],
          backgroundColor: ['#F7394B'   ]
        },
        {
          label: '"' + voteOptionNames[1] + '": ' + results.answerTwoVotes,
          data: [results.answerTwoVotes],
          backgroundColor: ['#3D3B3F']
        }
        ]
      }

      if(voteOptionNames[2]){
        data.datasets.push(        {
             label: '"' + voteOptionNames[2] + '": ' + results.answerThreeVotes,
             data: [results.answerThreeVotes],
             backgroundColor: ['#247F82']
           })
      }
      if(voteOptionNames[3]){
        data.datasets.push(
          {
            label: '"' + voteOptionNames[3] + '": ' + results.answerFourVotes,
            data: [results.answerFourVotes],
            backgroundColor: ['#4DC0B5']
          }
      )
      }

    let options = {
                      type: 'horizontalBar',
                      data: data,
//                      defaults: {
//                        defaultFontSize: 20
//                      },
                      options: {
                        drawTicks: false,
                        showLine: false,
                        responsive: true,
                        animation: {
                          duration: 2000,
                          easing: 'easeInOutQuad'
                        },
                        scales: {
                          xAxes: [{
                            stacked: true,
                            ticks: {

                              min: 0,
//                              max: 20
                            }
                          }],
                          yAxes: [{
                            stacked: true,

                            ticks: {
                              min: 0,
                              stepSize: 1
                            }
                          }]
                        },
                        legend: {
                          display: true,
                          labels: {
//                            fontFamily: 'font-family: \'Lato\', sans-serif',
                            fontSize: 20
                          }
                        }
                      },

                    }

    new Chart(ctx, options);

    setTimeout(reset, 5000);
}

function reset() {
        $('canvas').css('display','none');
        $('form').css('display','flex');
}

$(document).ready(setupClicks);