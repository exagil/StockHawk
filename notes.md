Notes
=====

Target - offline first android app
  - server
    - data refreshes every 24 hours
    - data is constant for 1 month right now

    - user has no internet connection
      - data available locally
        - show
      - data not available locally
        - show network error

    - user has internet connection
      - quotes available locally
        - fresh
          - do not query web api
        - not fresh
          - query web api for the quotes
      - quotes not available locally
        - fetch from web api
          - quotes status unknown
          - quotes status ok
          - quotes status server error
          - quotes status invalid
          - quotes status server down

choices:
- sync adapter
  why
    - obtain a completely different process to execute the fetching of historical quotes
    - will be able to use content providers without the pain of loaders
    - sync the historical data whenever required at specific intervals, on gcm request, on user request / immediately
  why not
    - strict dependency on android framework - no unit tests
    - yagni - is fetching of historical quotes even needed when the application is not visible
    - do you know that the user is actually willing to see the historical quotes that you are storing them unnecessarily
  * same applies for any service

- solution: retrofit with loaders

Historical Data from Yahoo API
